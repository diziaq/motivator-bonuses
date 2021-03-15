package se.fastdev.portal.motivator.bonuses.core.internal

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import se.fastdev.portal.motivator.bonuses.core.BonusesGate
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage
import se.fastdev.portal.motivator.bonuses.core.internal.util.ErrorRegistry
import se.fastdev.portal.motivator.bonuses.core.internal.util.PersonAttrSyncAccumulator
import se.fastdev.portal.motivator.bonuses.core.internal.util.SeqScanner
import se.fastdev.portal.motivator.bonuses.core.models.BulkProcessReport
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount
import se.fastdev.portal.motivator.bonuses.core.models.Person
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange
import java.util.function.Function
import java.util.stream.Stream

internal class SupportPersistent(private val storage: BonusesStorage) : BonusesGate.Support {

    override fun <T> refreshAttributes(
        offeredAttributes: Stream<PersonAttributes>,
        identityProbe: Function<PersonAttributes, T>
    ): Mono<BulkProcessReport> {

        val scanner = PersonAttrSyncAccumulator(offeredAttributes, identityProbe)
        val errors = ErrorRegistry()

        val scanResult = storage
            .findAll()
            .doOnNext(scanner::consume)
            .then(Mono.defer { Mono.just(scanner.snapshot()) })
            .cache()

        val processedCount = scanResult
            .flatMapMany { Flux.fromIterable(it.refreshCandidates) }
            .flatMap(storage::save)
            .onErrorContinue { e, _ -> errors.register(e) }
            .count()

        return Mono.zip(scanResult, processedCount) { sr, pc ->
            BulkProcessReport(
                skippedCount = sr.skippedCount,
                processedCount = pc,
                errors = errors.describe()
            )
        }
    }

    override fun refreshExpenseProfiles(
        offeredLimitAmount: MoneyAmount,
        offeredExpensePeriod: TimeRange
    ): Mono<BulkProcessReport> {

        val offeredProfile = ExpenseProfile(ExpenseProfile.Blueprint(offeredLimitAmount, offeredExpensePeriod))
        val newStart = offeredExpensePeriod.start
        val scanner = SeqScanner<Person>()

        val snapshot = storage
            .findAll()
            .doOnNext(scanner::onEvery)
            .filter { newStart >= it.activeExpenseProfile.periodOfActivity.finish }
            .doOnNext(scanner::onAccepted)
            .map { p -> p.startNewExpenseProfile(offeredProfile) }
            .flatMap(storage::save)
            .onErrorContinue { e, _ -> scanner.onError(e) }
            .then(Mono.defer { Mono.just(scanner.snapshot()) })

        return snapshot.map {
            BulkProcessReport(
                skippedCount = it.allCount - it.acceptedCount,
                processedCount = it.acceptedCount - it.errors.size,
                errors = it.errors
            )
        }
    }
}

package se.fastdev.portal.motivator.bonuses.core.internal

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import se.fastdev.portal.motivator.bonuses.core.BonusesGate
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage
import se.fastdev.portal.motivator.bonuses.core.models.BulkProcessReport
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes
import java.util.function.Function
import java.util.stream.Stream

internal class SupportPersistent(private val storage: BonusesStorage) : BonusesGate.Support {

    override fun <T> refreshAttributes(
        offeredAttributes: Stream<PersonAttributes>,
        identityProbe: Function<PersonAttributes, T>
    ): Mono<BulkProcessReport> {

        val scanner = FluxScanner(offeredAttributes, identityProbe)
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
}

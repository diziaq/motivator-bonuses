package se.fastdev.portal.motivator.bonuses.core.internal

import se.fastdev.portal.motivator.bonuses.core.BonusesException
import se.fastdev.portal.motivator.bonuses.core.BonusesRes
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile
import se.fastdev.portal.motivator.bonuses.core.models.MoneyAmount
import se.fastdev.portal.motivator.bonuses.core.models.TimeRange
import java.time.Instant
import java.time.Period
import java.util.UUID
import java.util.stream.Stream

class BonusesResBasic : BonusesRes {

    override fun currentTime(): Instant = Instant.now()

    override fun currentExpenseProfile(): ExpenseProfile.Blueprint {
        val currentTime = currentTime()
        val configuredSpendLimit = 50_000_00
        val configuredStartPoint = Instant.parse("2020-01-01T00:00:00Z")
        val stepPeriod = Period.ofMonths(6)

        val currentTimeRange = Stream
            .iterate(configuredStartPoint, { it.plus(stepPeriod) })
            .filter { it.isAfter(currentTime) }
            .map { TimeRange(it.minus(stepPeriod), it) }
            .findFirst()
            .orElseThrow { newException("Failed to find current period for expense profile", null) }

        return ExpenseProfile.Blueprint(
            MoneyAmount(configuredSpendLimit),
            currentTimeRange
        )
    }

    override fun availableExpenseTypes(): List<String> {
        return listOf("HEALTH", "SPORTS")
    }

    override fun newUuid(): UUID = UUID.randomUUID()

    override fun newException(message: String, cause: Exception?): BonusesException {
        return when (cause) {
            null -> BonusesException(message)
            else -> BonusesException(message, cause)
        }
    }
}

package se.fastdev.portal.motivator.bonuses.core.models

import se.fastdev.portal.motivator.bonuses.core.BonusesException
import java.time.Instant

data class ExpenseProfile(
    val limit: MoneyAmount,
    val periodOfActivity: TimeRange,
    val expenses: List<ExpenseItem>
) {
    private val remainder: MoneyAmount by lazy(this::calculateRemainder)

    fun remainder(): MoneyAmount = remainder

    private fun calculateRemainder(): MoneyAmount {
        val sum = expenses.map { it.amount }.fold(MoneyAmount.zero()) { a, b -> a + b }
        val remainder = limit - sum

        return if (remainder.asInt() < 0)
            throw BonusesException("Remainder cannot be negative: limit $limit, totalSum $sum")
        else remainder
    }

    fun addExpense(expenseItem: ExpenseItem) =
        copy(expenses = validHead(expenseItem) + expenses)

    private fun validHead(expenseItem: ExpenseItem): List<ExpenseItem> {
        val expenseTime = expenseItem.spentStamp.at
        val newRemainder = this.remainder - expenseItem.amount

        val result =
            if (!periodOfActivity.contains(expenseTime))
                throw BonusesException("Expense time $expenseTime is out of the period $periodOfActivity")
            else if (newRemainder.asInt() < 0)
                throw BonusesException("Expense amount ${expenseItem.amount} exceeds remaining sum $remainder")
            else
                listOf(expenseItem)

        return result
    }

    companion object {
        operator fun invoke(blueprint: Blueprint) =
            ExpenseProfile(
                limit = blueprint.limit,
                periodOfActivity = blueprint.periodOfActivity,
                expenses = emptyList()
            )
    }

    data class Blueprint(
        val limit: MoneyAmount,
        val periodOfActivity: TimeRange
    ) {
        companion object {
            fun empty(time: Instant = Instant.now()) =
                Blueprint(MoneyAmount(0), TimeRange(time, time))
        }
    }
}

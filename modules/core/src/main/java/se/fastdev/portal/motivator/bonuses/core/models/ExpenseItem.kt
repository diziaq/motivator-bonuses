package se.fastdev.portal.motivator.bonuses.core.models

import java.util.UUID

data class ExpenseItem(
    val uuid: UUID,
    val type: ExpenseType,
    val amount: MoneyAmount,
    val description: String,
    val spentStamp: ActionStamp,
    val publishedStamp: ActionStamp
) {
    data class Blueprint(
        val type: ExpenseType,
        val amount: MoneyAmount,
        val description: String,
        val spentStamp: ActionStamp
    )
}

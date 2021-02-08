package se.fastdev.portal.motivator.bonuses.core.models

import java.util.UUID

data class Person(
    val uuid: UUID,
    val attributes: PersonAttributes,
    val activeExpenseProfile: ExpenseProfile,
    val expenseProfilesHistory: List<ExpenseProfile>
) {

    constructor(uuid: UUID, attributes: PersonAttributes) :
        this(
            uuid = uuid,
            attributes = attributes,
            activeExpenseProfile = ExpenseProfile(ExpenseProfile.Blueprint.empty()),
            expenseProfilesHistory = emptyList()
        )

    fun startNewExpenseProfile(expenseProfile: ExpenseProfile) =
        copy(
            activeExpenseProfile = expenseProfile,
            expenseProfilesHistory = listOf(activeExpenseProfile) + expenseProfilesHistory
        )
}

package se.fastdev.portal.motivator.bonuses.core.internal

import se.fastdev.portal.motivator.bonuses.core.models.ActionStamp
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseItem
import se.fastdev.portal.motivator.bonuses.core.models.ExpenseProfile
import se.fastdev.portal.motivator.bonuses.core.models.Person
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes
import java.util.UUID

internal class AdministerHelp {

    fun createNewPerson(attributes: PersonAttributes) =
        Person(attributes)

    fun startNewExpenseProfile(person: Person, blueprint: ExpenseProfile.Blueprint) =
        person.startNewExpenseProfile(ExpenseProfile(blueprint))

    fun addNewExpenseItem(
        person: Person,
        blueprint: ExpenseItem.Blueprint,
        publisherId: String = "unrecognized publisher"
    ): Person {
        val expenseItem = ExpenseItem(
            uuid = UUID.randomUUID(),
            type = blueprint.type,
            amount = blueprint.amount,
            description = blueprint.description,
            spentStamp = blueprint.spentStamp,
            publishedStamp = ActionStamp(by = publisherId)
        )

        return person.copy(activeExpenseProfile = person.activeExpenseProfile.addExpense(expenseItem))
    }
}

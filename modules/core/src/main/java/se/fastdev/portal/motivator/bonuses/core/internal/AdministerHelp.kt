package se.fastdev.portal.motivator.bonuses.core.internal

import se.fastdev.portal.motivator.bonuses.core.models.Person
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes

internal class AdministerHelp {

    fun createNewPerson(attributes: PersonAttributes) =
        Person(attributes)
}

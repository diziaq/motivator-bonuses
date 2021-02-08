package se.fastdev.portal.motivator.bonuses.core.models

import java.util.UUID

data class PersonAttributes(
    val portalId: String,
    val firstName: String,
    val lastName: String,
    val location: String
) {
    constructor(firstName: String, lastName: String, location: String) :
        this(
            portalId = unknownRandomId(),
            firstName = firstName,
            lastName = lastName,
            location = location
        )

    operator fun compareTo(other: PersonAttributes): Int {
        var value = 0

        value = if (value != 0) value else this.portalId.compareTo(other.portalId)
        value = if (value != 0) value else this.lastName.compareTo(other.lastName)
        value = if (value != 0) value else this.firstName.compareTo(other.firstName)
        value = if (value != 0) value else this.location.compareTo(other.location)

        return value
    }

    override fun toString() =
        "($portalId, $firstName, $lastName, $location)"

    companion object {
        private fun unknownRandomId() =
            "random-test-id-" + UUID.randomUUID()
    }
}

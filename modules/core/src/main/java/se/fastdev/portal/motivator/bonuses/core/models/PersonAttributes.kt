package se.fastdev.portal.motivator.bonuses.core.models

data class PersonAttributes(
    val firstName: String,
    val lastName: String,
    val location: String
) {
    operator fun compareTo(other: PersonAttributes): Int {
        var value = 0

        value = if (value != 0) value else this.lastName.compareTo(other.lastName)
        value = if (value != 0) value else this.firstName.compareTo(other.firstName)
        value = if (value != 0) value else this.location.compareTo(other.location)

        return value
    }

    override fun toString() =
        "($firstName, $lastName, $location)"
}

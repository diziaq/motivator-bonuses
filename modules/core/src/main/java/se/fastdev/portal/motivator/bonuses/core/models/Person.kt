package se.fastdev.portal.motivator.bonuses.core.models

import java.util.UUID

data class Person(
    val uuid: UUID,
    val attributes: PersonAttributes
) {
    companion object {
        operator fun invoke(attributes: PersonAttributes) =
            Person(
                uuid = UUID.randomUUID(),
                attributes = attributes
            )
    }
}

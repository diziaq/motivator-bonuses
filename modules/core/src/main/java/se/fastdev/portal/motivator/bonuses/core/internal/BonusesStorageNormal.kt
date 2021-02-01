package se.fastdev.portal.motivator.bonuses.core.internal

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import se.fastdev.portal.motivator.bonuses.core.BonusesException
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage
import se.fastdev.portal.motivator.bonuses.core.models.Person
import java.util.UUID

internal class BonusesStorageNormal(
    private val storage: BonusesStorage
) : BonusesStorage {

    override fun save(person: Person): Mono<Person> =
        storage.save(person)
            .onErrorMap { e -> BonusesException("Unable to save person ${person.attributes}", e) }

    override fun findAll(): Flux<Person> =
        storage.findAll()
            .onErrorMap { e -> BonusesException("Unable to get all persons", e) }

    override fun findByUuid(uuid: UUID): Mono<Person> =
        storage.findByUuid(uuid)
            .onErrorMap { e -> BonusesException("Unable to get person for id $uuid", e) }
}

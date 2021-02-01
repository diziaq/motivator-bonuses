package se.fastdev.portal.motivator.bonuses.core.internal

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import se.fastdev.portal.motivator.bonuses.core.BonusesGate.Administer
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage
import se.fastdev.portal.motivator.bonuses.core.models.Person
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes
import java.util.UUID

internal class AdministerPersistent(
    private val storage: BonusesStorage,
    private val help: AdministerHelp
) : Administer {

    override fun createNewPerson(attributes: PersonAttributes): Mono<Person> =
        storage.save(help.createNewPerson(attributes))

    override fun getAllPersons(): Flux<Person> =
        storage.findAll().sort { a, b -> a.attributes.compareTo(b.attributes) }

    override fun getPersonById(uuid: UUID): Mono<Person> =
        storage.findById(uuid)
}

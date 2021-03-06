package se.fastdev.portal.motivator.bonuses.core.internal

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage
import se.fastdev.portal.motivator.bonuses.core.models.Person
import se.fastdev.portal.motivator.bonuses.toolbox.exceptions.CommonException
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

internal class BonusesStorageSimpleInMemory : BonusesStorage {
    private val personMap: MutableMap<UUID, Person> = ConcurrentHashMap()

    override fun save(person: Person): Mono<Person> {
        personMap[person.uuid] = person
        return findByUuid(person.uuid)
    }

    override fun findByPortalId(portalId: String): Mono<Person> =
        findAll()
            .filter { it.attributes.portalId == portalId }
            .single()

    override fun findAll(): Flux<Person> =
        Flux.fromIterable(personMap.values)

    override fun findByUuid(uuid: UUID): Mono<Person> {
        return Mono.just(uuid)
            .handle { x: UUID, sink: SynchronousSink<Person> ->
                val person = personMap[x]
                if (person == null) {
                    sink.error(CommonException.thin("Not found person with id $uuid"))
                } else {
                    sink.next(person)
                }
            }
    }
}

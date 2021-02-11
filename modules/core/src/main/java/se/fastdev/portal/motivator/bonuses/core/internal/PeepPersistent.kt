package se.fastdev.portal.motivator.bonuses.core.internal

import reactor.core.publisher.Mono
import se.fastdev.portal.motivator.bonuses.core.BonusesGate
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage
import se.fastdev.portal.motivator.bonuses.core.models.Person

internal class PeepPersistent(
    private val portalId: String,
    private val storage: BonusesStorage
) : BonusesGate.Peep {
    override fun getOwnedPerson(): Mono<Person> {
        return storage.findByPortalId(portalId)
    }
}

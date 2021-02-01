package se.fastdev.portal.motivator.bonuses.core.internal

import se.fastdev.portal.motivator.bonuses.core.BonusesGate
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage

internal class BonusesGatePersistent(
    private val storage: BonusesStorage,
    private val administerHelp: AdministerHelp = AdministerHelp()
) : BonusesGate {
    override fun administer() =
        AdministerPersistent(storage, administerHelp)
}

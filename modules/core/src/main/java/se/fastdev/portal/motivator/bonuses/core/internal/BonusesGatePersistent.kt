package se.fastdev.portal.motivator.bonuses.core.internal

import se.fastdev.portal.motivator.bonuses.core.BonusesGate
import se.fastdev.portal.motivator.bonuses.core.BonusesRes
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage

internal class BonusesGatePersistent(
    private val storage: BonusesStorage,
    private val res: BonusesRes,
    private val administerHelp: AdministerHelp = AdministerHelp()
) : BonusesGate {

    override fun administer() =
        AdministerPersistent(storage, res, administerHelp)

    override fun peep(portalId: String) =
        PeepPersistent(portalId, storage, res)

    override fun support() =
        SupportPersistent(storage, res)
}

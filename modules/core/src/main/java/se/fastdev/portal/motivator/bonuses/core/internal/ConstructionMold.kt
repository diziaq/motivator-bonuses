package se.fastdev.portal.motivator.bonuses.core.internal

import se.fastdev.portal.motivator.bonuses.core.BonusesGate
import se.fastdev.portal.motivator.bonuses.core.BonusesRes
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage

object ConstructionMold {
    fun gateWithStorage(storage: BonusesStorage, res: BonusesRes): BonusesGate {
        val normalStorage = BonusesStorageNormal(storage)
        return BonusesGatePersistent(normalStorage, res)
    }

    fun storageInMemory(): BonusesStorage {
        return BonusesStorageSimpleInMemory()
    }
}

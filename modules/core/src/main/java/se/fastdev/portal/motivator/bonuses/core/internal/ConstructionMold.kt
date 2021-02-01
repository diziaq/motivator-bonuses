package se.fastdev.portal.motivator.bonuses.core.internal

import se.fastdev.portal.motivator.bonuses.core.BonusesGate
import se.fastdev.portal.motivator.bonuses.core.BonusesStorage

object ConstructionMold {
    fun gateWithStorage(storage: BonusesStorage): BonusesGate {
        val normalStorage = BonusesStorageNormal(storage)
        return BonusesGatePersistent(normalStorage)
    }

    fun storageInMemory(): BonusesStorage {
        return BonusesStorageSimpleInMemory()
    }
}

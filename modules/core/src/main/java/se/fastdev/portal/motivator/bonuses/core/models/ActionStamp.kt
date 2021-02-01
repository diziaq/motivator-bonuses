package se.fastdev.portal.motivator.bonuses.core.models

import java.time.Instant

data class ActionStamp(
    val at: Instant = Instant.now(),
    val by: String?
)

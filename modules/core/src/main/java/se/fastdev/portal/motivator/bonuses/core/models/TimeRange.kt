package se.fastdev.portal.motivator.bonuses.core.models

import java.time.Instant

data class TimeRange(
    val start: Instant,
    val finish: Instant
) {
    fun contains(point: Instant) =
        start.isBefore(point) && finish.isAfter(point)

    override fun toString() = "($start :: $finish)"
}

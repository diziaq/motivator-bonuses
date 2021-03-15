package se.fastdev.portal.motivator.bonuses.core.internal.util

import java.util.concurrent.atomic.AtomicLong

internal class SeqScanner<T> {
    private val everyCounter = AtomicLong(0L)
    private val acceptedCounter = AtomicLong(0L)
    private val errorRegistry = ErrorRegistry()

    fun onEvery(_item: T) {
        everyCounter.incrementAndGet()
    }

    fun onAccepted(_item: T) {
        acceptedCounter.incrementAndGet()
    }

    fun onError(error: Throwable) {
        errorRegistry.register(error)
    }

    fun snapshot() =
        Snapshot(
            everyCounter.get(),
            acceptedCounter.get(),
            errorRegistry.describe()
        )

    data class Snapshot(
        val allCount: Long,
        val acceptedCount: Long,
        val errors: List<String>
    )
}

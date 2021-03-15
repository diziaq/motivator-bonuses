package se.fastdev.portal.motivator.bonuses.core.internal.util

import se.fastdev.portal.motivator.bonuses.core.models.Person
import se.fastdev.portal.motivator.bonuses.core.models.PersonAttributes
import java.util.UUID
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Function
import java.util.stream.Collectors.toMap
import java.util.stream.Stream

internal class PersonAttrSyncAccumulator<T>(
    offeredAttributes: Stream<PersonAttributes>,
    private val identityProbe: Function<PersonAttributes, T>
) {
    private val idToAttr: Map<T, PersonAttributes> = offeredAttributes.collect(toMap({ identityProbe.apply(it) }, { it }))

    private val skippedCount = AtomicLong(0L)
    private val candidates = ConcurrentLinkedDeque<Person>()
    private val hitIds = ConcurrentLinkedDeque<T>()

    fun consume(p: Person) {
        val currAttr = p.attributes
        val id = identityProbe.apply(currAttr)
        hitIds.add(id)

        when (val newAttr = idToAttr[id]) {
            null -> {
                // no match - pass through
            }
            currAttr -> {
                skippedCount.incrementAndGet()
            }
            else -> {
                candidates.add(p.copy(attributes = newAttr))
            }
        }
    }

    fun snapshot(): Result {
        val insertCandidates = idToAttr.minus(hitIds).values.map { Person(UUID.randomUUID(), it) }
        val updateCandidates = candidates.toList()
        return Result(skippedCount.get(), insertCandidates + updateCandidates)
    }

    data class Result(
        val skippedCount: Long,
        val refreshCandidates: List<Person>
    )
}

package se.fastdev.portal.motivator.bonuses.core.internal

import se.fastdev.portal.motivator.bonuses.toolbox.conversion.ThrowablesUtil
import java.util.concurrent.ConcurrentLinkedDeque

internal class ErrorRegistry {

    private val errors = ConcurrentLinkedDeque<String>()

    fun register(err: Throwable) {
        val errMessage = ThrowablesUtil.causesChain(err).take(500)
        errors.add(errMessage)
    }

    fun describe() = errors.toList()
}

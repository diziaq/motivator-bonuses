package se.fastdev.portal.motivator.bonuses.core

class BonusesException : RuntimeException {

    constructor(message: String, cause: Throwable) : super(message, cause)
}

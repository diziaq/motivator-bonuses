package se.fastdev.portal.motivator.bonuses.core

class BonusesException : RuntimeException {

    constructor(message: String, cause: Exception) : super(message, cause)

    constructor(message: String) : super(message)
}

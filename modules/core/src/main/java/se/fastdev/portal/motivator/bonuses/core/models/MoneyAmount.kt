package se.fastdev.portal.motivator.bonuses.core.models

import java.math.BigDecimal

data class MoneyAmount(private val amount: Int) {

    constructor(amount: BigDecimal) : this(amount.multiply(HUNDRED).toInt())

    fun asBigDecimal(): BigDecimal = BigDecimal(amount).divide(HUNDRED)

    fun asInt() = amount

    operator fun plus(other: MoneyAmount) = MoneyAmount(this.amount + other.amount)

    operator fun minus(other: MoneyAmount) = MoneyAmount(this.amount - other.amount)

    override fun toString() = "(${this.asBigDecimal()})"

    companion object {
        private val HUNDRED = BigDecimal(100)
        fun zero(): MoneyAmount = MoneyAmount(0)
    }
}

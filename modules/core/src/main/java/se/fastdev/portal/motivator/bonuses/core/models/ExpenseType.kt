package se.fastdev.portal.motivator.bonuses.core.models

// TODO: store editable list in DB
enum class ExpenseType(private val title: String) {
    SPORTS("Sports"),
    HEALTHCARE("Healthcare");

    override fun toString() = title

    companion object {
        fun byName(name: String) = valueOf(name.toUpperCase())
    }
}

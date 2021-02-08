package se.fastdev.portal.motivator.bonuses.core.models

data class BulkProcessReport(
    val skippedCount: Long,
    val processedCount: Long,
    val errors: List<String>
)

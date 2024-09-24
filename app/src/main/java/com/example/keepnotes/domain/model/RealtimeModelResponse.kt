package com.example.keepnotes.domain.model
data class RealtimeModelResponse(
    val item: RealtimeItems?,
    val key: String? = ""
) {
    // Remove the following line
    // val category: String = ""

    data class RealtimeItems(
        val userId: String? = "",
        val title: String? = "",
        val note: String? = "",
        val category: String?, // This is correct
        val createdAt: Long? = System.currentTimeMillis(),
        var updatedAt: Long? = System.currentTimeMillis(),
    )
}


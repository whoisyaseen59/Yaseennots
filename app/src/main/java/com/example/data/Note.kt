package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val htmlContent: String = "",
    val description: String = "",
    val tags: String = "", // Comma-separated tags
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val color: Long = 0xFFFFFFFF,
    val isPinned: Boolean = false,
    val reminderDateTime: Long? = null,
    val filePath: String? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Long? = null,
    val notebookId: String? = null,
    val drawingData: String? = null // SVG/Canvas stroke points serialization
) {
    fun getTagList(): List<String> {
        if (tags.isBlank()) return emptyList()
        return tags.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    }
}

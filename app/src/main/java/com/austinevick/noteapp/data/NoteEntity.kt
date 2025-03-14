package com.austinevick.noteapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val color: String = "#059200",
    val isArchived: Boolean,
    val isLocked: Boolean,
    val isPinned: Boolean,
    val date: Long = System.currentTimeMillis()
)

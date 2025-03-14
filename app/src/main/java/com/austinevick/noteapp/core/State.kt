package com.austinevick.noteapp.core



sealed interface UiState<out T>{
    data class Success<out T>(val data: T): UiState<T>
    data class Error(val message: String): UiState<Nothing>
    object Loading: UiState<Nothing>
    object None: UiState<Nothing>
}

data class NoteActionState(
    val message: String? = null
)


data class NoteState(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val color: String = "#059200",
    var isPinned: Boolean = false,
    var isLocked: Boolean = false,
    var isArchived: Boolean = false
)
package com.austinevick.noteapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.austinevick.noteapp.core.NoteActionState
import com.austinevick.noteapp.core.NoteState
import com.austinevick.noteapp.data.NoteEntity
import com.austinevick.noteapp.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _noteActionState = MutableStateFlow(NoteActionState())
    val noteActionState = _noteActionState

    var noteUiState = MutableStateFlow(NoteState())
        private set


    fun pinNote() {
        noteUiState.value = noteUiState.value
            .copy(isPinned = !noteUiState.value.isPinned)
    }

    fun archiveNote() {
        noteUiState.value = noteUiState.value
            .copy(isArchived = !noteUiState.value.isArchived)
    }

    fun updateNoteTitle(title: String) {
        noteUiState.value = noteUiState.value.copy(title = title)
    }

    fun updateNoteContent(content: String) {
        noteUiState.value = noteUiState.value.copy(content = content)
    }

    fun updateNoteColor(color: String) {
        noteUiState.value = noteUiState.value.copy(color = color)
    }


    fun getNote(id: Int) {
        viewModelScope.launch {
            try {
                noteRepository.getNoteById(id).collect {
                    noteUiState.value = NoteState(
                        id = it.id.toString(),
                        title = it.title,
                        content = it.content,
                        color = it.color,
                        isPinned = it.isPinned,
                        isArchived = it.isArchived
                    )
                }
            } catch (e: Exception) {
                _noteActionState.update {
                    it.copy(message = e.message ?: "Unknown error")
                }
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(noteUiState.value.id.toInt())
                _noteActionState.value = NoteActionState(message = "Note deleted successfully")
            } catch (e: Exception) {
                _noteActionState.value = NoteActionState(message = e.message ?: "Unknown error")
            }
        }
    }

    fun updateNote() {
        viewModelScope.launch {
            try {
                val note = NoteEntity(
                    id = noteUiState.value.id.toInt(),
                    title = noteUiState.value.title,
                    content = noteUiState.value.content,
                    color = noteUiState.value.color,
                    isPinned = noteUiState.value.isPinned,
                    isArchived = noteUiState.value.isArchived
                )
                if (note.title.isBlank() && note.content.isBlank()) {
                    _noteActionState.update {
                        it.copy(message = "Empty note are discarded")
                    }
                    return@launch
                }
                noteRepository.updateNote(note)
                _noteActionState.value = NoteActionState(message = "Note updated successfully")
            } catch (e: Exception) {
                _noteActionState.update {
                    it.copy(message = e.message ?: "Unknown error")
                }
            }
        }
    }


    fun addNote() {
        viewModelScope.launch {
            val note = NoteEntity(
                title = noteUiState.value.title,
                content = noteUiState.value.content,
                color = noteUiState.value.color,
                isPinned = noteUiState.value.isPinned,
                isArchived = noteUiState.value.isArchived
            )
            try {
                if (note.title.isBlank() && note.content.isBlank()) {
                    _noteActionState.update {
                        it.copy(message = "Empty note are discarded")
                    }
                    return@launch
                }
                noteRepository.insertNote(note)
                _noteActionState.value = NoteActionState(message = "Note added successfully")
            } catch (e: Exception) {
                _noteActionState.value = NoteActionState(message = e.message ?: "Unknown error")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("EditNoteViewModel", "ViewModel cleared")
        noteUiState.value = NoteState()
    }

}
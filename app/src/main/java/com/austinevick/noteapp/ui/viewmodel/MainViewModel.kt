package com.austinevick.noteapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.austinevick.noteapp.core.NoteActionState
import com.austinevick.noteapp.core.NoteState
import com.austinevick.noteapp.core.UiState
import com.austinevick.noteapp.data.NoteEntity
import com.austinevick.noteapp.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _noteListState = MutableStateFlow<UiState<List<NoteEntity>>>(UiState.Loading)
    val noteListState = _noteListState

    private val _searchNoteState = MutableStateFlow<UiState<List<NoteEntity>>>(UiState.None)
    val searchNoteState = _searchNoteState

    private val _archivedNoteListState =
        MutableStateFlow<UiState<List<NoteEntity>>>(UiState.Loading)
    val archivedNoteListState = _archivedNoteListState

    private val _lockedNoteListState = MutableStateFlow<UiState<List<NoteEntity>>>(UiState.Loading)
    val lockedNoteListState = _lockedNoteListState

    private val _noteActionState = MutableStateFlow(NoteActionState())
    val noteActionState = _noteActionState

    var searchQuery = mutableStateOf("")
        private set


    init {
        getNotes()
        getArchivedNotes()
        getLockedNotes()
    }


    private fun getNotes() {
        viewModelScope.launch {
            try {
                noteRepository.getNotes().collect { notes ->
                    _noteListState.value = UiState.Success(notes)
                }
            } catch (e: Exception) {
                _noteListState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }



    private fun getLockedNotes(){
        viewModelScope.launch {
            try {
                noteRepository.getLockedNotes().collect{notes ->
                    _lockedNoteListState.value = UiState.Success(notes)
                }
            }catch (e: Exception){
                _lockedNoteListState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun getArchivedNotes() {
        viewModelScope.launch {
            try {
                noteRepository.getArchivedNotes().collect { notes ->
                    _archivedNoteListState.value = UiState.Success(notes)
                }
            } catch (e: Exception) {
                _archivedNoteListState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
        searchNotes(query)
    }

    fun searchNotes(query: String) {
        viewModelScope.launch {
            try {
                noteRepository.searchNotes(query).collect { notes ->
                    _searchNoteState.value = UiState.Success(notes)
                    Log.d("MainViewModel", "searchNotes: $notes")
                }
            } catch (e: Exception) {
                _searchNoteState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun archiveNote(note: NoteEntity) {
        viewModelScope.launch {
            try {
                noteRepository.archiveNote(note.id)
                _noteActionState.value = NoteActionState(message = "Note archived successfully")
            } catch (e: Exception) {
                _noteActionState.value = NoteActionState(message = e.message ?: "Unknown error")
            }
        }
    }


}
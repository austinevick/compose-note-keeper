package com.austinevick.noteapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.austinevick.noteapp.preference.NotePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasscodeViewModel @Inject constructor(
    private val application: Application,
    private val preferences: NotePreferences
) : ViewModel() {


    var passcode = MutableStateFlow("")
        private set
    var confirmPasscode = MutableStateFlow("")
        private set
    var errorMessage = MutableStateFlow("")
        private set

    private val _savedPasscode = MutableStateFlow("")
    val savedPasscode = _savedPasscode


    init {
        getPasscode()
    }

    fun savePasscode(passcode: String) {
        viewModelScope.launch {
            preferences.saveToDataStore(application, passcode)
        }
    }

    fun getPasscode() {
        viewModelScope.launch {
            preferences.getFromDataStore.collect {
                _savedPasscode.value = it
            }
        }
    }


    fun setPasscode(newPasscode: String) {
        passcode.value = newPasscode
    }

    fun setConfirmPasscode(newPasscode: String) {
        confirmPasscode.value = newPasscode
    }

    fun setErrorMessage(newMessage: String) {
        errorMessage.value = newMessage
        Log.d("error", errorMessage.value)
    }

}


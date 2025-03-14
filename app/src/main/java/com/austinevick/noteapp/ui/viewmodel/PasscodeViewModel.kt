package com.austinevick.noteapp.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PasscodeViewModel @Inject constructor() : ViewModel() {

    val digits = 4
    var passcode = MutableStateFlow("")
        private set


    fun onButtonClick(i: Int) {
        when (i) {
            11 -> {
                // Delete passcode starting from the last digit
                if (passcode.value.isNotBlank())
                    passcode.value = passcode.value.substring(0, passcode.value.length - 1)
            }

            10 -> {
                if (passcode.value.length == digits) return
                passcode.value += (0).toString()
            }

            else -> {
                if (passcode.value.length == digits) return
                passcode.value += (i + 1).toString()
            }
        }
    }

}


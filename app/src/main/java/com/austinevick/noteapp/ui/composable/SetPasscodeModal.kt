package com.austinevick.noteapp.ui.composable

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.austinevick.noteapp.theme.Green
import com.austinevick.noteapp.ui.ConfirmPasscodeModal
import com.austinevick.noteapp.ui.CreatePasscodeModal
import com.austinevick.noteapp.ui.viewmodel.PasscodeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPasscodeModal(

    onDismiss: () -> Unit
) {
    val showModal = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val passcode = remember { mutableStateOf("") }
    val confirmPasscode = remember { mutableStateOf("") }


    LaunchedEffect(passcode.value, confirmPasscode.value) {
        if (confirmPasscode.value.length == 4) {
            if (passcode.value == confirmPasscode.value) {
                showDialog.value = true
            }
        }
    }


    ModalBottomSheet(
        shape = RoundedCornerShape(0.dp),
        containerColor = Color.White,
        onDismissRequest = onDismiss
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            Text(text = "Set a passcode to protect your notes",
                fontWeight = FontWeight.W600)
            HorizontalDivider(
                modifier = Modifier.padding(vertical =  16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f)),
                    onClick = onDismiss) {
                    Text(text = "Cancel",color = Green)
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f)),
                    onClick = {
                    showModal.value = true
                }) {
                    Text(text = "Create Passcode", color = Green)
                }
            }

            if (showModal.value) {
                CreatePasscodeModal(
                    passcode = passcode.value,
                    onConfirmPass = { confirmPasscode.value = it },
                    onConfirm = { passcode.value = it },
                    onDismiss = {
                        onDismiss()
                        showModal.value = false
                    }) }

            if (showDialog.value) {
                SuccessDialog(onDismiss = {
                    onDismiss()
                    showDialog.value = false
                })
            }
        }
    }

}
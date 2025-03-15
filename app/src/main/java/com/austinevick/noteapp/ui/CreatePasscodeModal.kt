package com.austinevick.noteapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.austinevick.noteapp.ui.composable.PasscodeWidget
import com.austinevick.noteapp.ui.viewmodel.PasscodeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePasscodeModal(
    viewModel: PasscodeViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {},
    passcode: String = "",
    onConfirm: (String) -> Unit = {},
    onConfirmPass: (String) -> Unit = {},
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val isVisible = remember { mutableStateOf(false) }

    LaunchedEffect(passcode) {
        if (passcode.length == 4) {
            isVisible.value = true
        }
    }


    ModalBottomSheet(
        sheetState = sheetState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 250.dp),
        shape = RoundedCornerShape(0.dp),
        containerColor = Color.White,
        onDismissRequest = onDismiss
    ) {
        BackHandler {
            if (isVisible.value) {
                isVisible.value = false
                viewModel.setErrorMessage("")
                viewModel.setConfirmPasscode("")
                viewModel.setPasscode("")
            } else {
                onDismiss()
            }
        }
        if (isVisible.value)
            PasscodeWidget(
                title = "Confirm Passcode",
                isVisible = isVisible.value,
                onBackButtonClick = {
                    isVisible.value = false
                    viewModel.setErrorMessage("")
                    viewModel.setConfirmPasscode("")
                    viewModel.setPasscode("")
                },
                onButtonClick = { onConfirmPass(it) })
        else
            PasscodeWidget(
                isVisible = isVisible.value,
                title = "Enter Passcode",
                onButtonClick = { onConfirm(it) })
    }

}


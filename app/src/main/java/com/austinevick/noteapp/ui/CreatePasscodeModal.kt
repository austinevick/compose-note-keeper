package com.austinevick.noteapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.austinevick.noteapp.ui.composable.PasscodeWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePasscodeModal(
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
            .padding(top = 300.dp),
        shape = RoundedCornerShape(0.dp),
        containerColor = Color.White,
        onDismissRequest = onDismiss
    ) {
        if (isVisible.value)
            PasscodeWidget(
                title = "Confirm Passcode",
                onButtonClick = { onConfirmPass(it) })
        else
            PasscodeWidget(
                title = "Enter Passcode",
                onButtonClick = { onConfirm(it) })
    }

}


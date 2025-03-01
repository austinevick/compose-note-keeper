package com.austinevick.noteapp.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeleteModal (onDismiss: () -> Unit, onConfirm: () -> Unit){
    ModalBottomSheet(onDismiss,
        shape = RoundedCornerShape(0.dp),
        containerColor = Color.White,
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)) {
            Text(text = "Are you sure you want to delete this note?",
                fontWeight = FontWeight.W600)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = onDismiss) {
                    Text(text = "Cancel",color = Color.Black)
                }
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                    onConfirm()
                    onDismiss()
                }) {
                    Text(text = "Delete", color = Color.Red)
                }

            }
        }
    }
}
package com.austinevick.noteapp.ui.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.austinevick.noteapp.R
import com.austinevick.noteapp.theme.Green

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {

    Dialog(onDismissRequest = onDismiss) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp))
                .size(300.dp)
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Green)
                ) {
                    Image(
                        painterResource(R.drawable.check), null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(50.dp)
                            .animateContentSize()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("You are all set")
            }
        }
    }


}
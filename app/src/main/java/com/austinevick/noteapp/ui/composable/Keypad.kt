package com.austinevick.noteapp.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.austinevick.noteapp.R

@Composable
fun Keypad(
    i: Int,
    onClick: () -> Unit = {}
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(Color.LightGray.copy(0.2f), CircleShape)
            .size(55.dp)
            .clickable { onClick() }
    ) {
        when (i) {
            11 -> {
                Image(
                    painter = painterResource(
                        id = R.drawable.keyboard_backspace), null)
            }
            9 -> {
                Image(
                    painter = painterResource(
                        id = R.drawable.fingerprint), null)
            }

            10 -> {
                Text(
                    text = "0", fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            else -> {
                Text(
                    text = "${i + 1}", fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
package com.austinevick.noteapp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.austinevick.noteapp.R

@Composable
fun PasscodeWidget(
    onButtonClick: (String) -> Unit = {},
    title: String
) {

    val passcode = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            repeat(4) { i ->
                val isActive = passcode.value.length > i
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .background(Color.LightGray.copy(0.2f), CircleShape)
                        .padding(10.dp)
                        .size(20.dp)
                ) {
                    if (isActive) Icon(
                        painterResource(id = R.drawable.circle),
                        null, modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        NonLazyGrid(columns = 3, itemCount = 12) { i ->

            when (i) {
                11 -> {
                    if (passcode.value.isNotEmpty())
                        Keypad(i, onClick = {
                            if (passcode.value.isNotBlank()){
                                onButtonClick(passcode.value)
                                passcode.value =
                                    passcode.value.substring(0, passcode.value.length - 1)
                            }
                        })
                }

                9 -> {}

                10 -> {
                    Keypad(i, onClick = {
                        if (passcode.value.length == 4){
                            return@Keypad
                        }
                        passcode.value += (0).toString()
                        onButtonClick(passcode.value)
                    })
                }

                else -> {
                    Keypad(i, onClick = {
                        if (passcode.value.length == 4) return@Keypad
                        passcode.value += (i + 1).toString()
                        onButtonClick(passcode.value)

                    })
                }

            }

        }
    }
}
package com.austinevick.noteapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.austinevick.noteapp.R
import com.austinevick.noteapp.navigation.Routes
import com.austinevick.noteapp.ui.composable.Keypad
import com.austinevick.noteapp.ui.composable.NonLazyGrid
import com.austinevick.noteapp.ui.viewmodel.PasscodeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasscodeScreen(
    viewModel: PasscodeViewModel = hiltViewModel(),
    navController: NavHostController) {

    val passcode = remember { mutableStateOf("") }
    val savedPasscode = viewModel.savedPasscode.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(passcode.value) {
        if (passcode.value.length == 4) {
            if (passcode.value == savedPasscode.value) {
                navController.
                navigate(Routes.LockedNoteScreen.route){
                    popUpTo(Routes.PasscodeScreen.route){
                        inclusive = true
                    }
                }
            } else {
                snackbarHostState.showSnackbar("Incorrect passcode")
                passcode.value = ""
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults
                    .topAppBarColors(containerColor = Color.White),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painterResource(R.drawable.keyboard_backspace),
                            null, modifier = Modifier.size(28.dp)
                        )
                    }
                },
                title = { Text(text = "Enter Passcode") })
        }

        )
    { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {


            Spacer(modifier = Modifier.height(16.dp))
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
                                if (passcode.value.isNotBlank()) {
                                    passcode.value =
                                        passcode.value.substring(0, passcode.value.length - 1)
                                }
                            })
                    }

                    9 -> {}

                    10 -> {
                        Keypad(i, onClick = {
                            if (passcode.value.length == 4) {
                                return@Keypad
                            }
                            passcode.value += (0).toString()
                        })
                    }

                    else -> {
                        Keypad(i, onClick = {
                            if (passcode.value.length == 4) return@Keypad
                            passcode.value += (i + 1).toString()
                        })
                    }

                }

            }
        }

    }
}


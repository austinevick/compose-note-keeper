package com.austinevick.noteapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.austinevick.noteapp.R
import com.austinevick.noteapp.core.UiState
import com.austinevick.noteapp.navigation.Routes
import com.austinevick.noteapp.ui.composable.EmptyWidget
import com.austinevick.noteapp.ui.composable.NoteCard
import com.austinevick.noteapp.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchivedScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {

    val uiState = viewModel.archivedNoteListState.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults
                .topAppBarColors(containerColor = Color.White),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painterResource(R.drawable.keyboard_backspace),
                            null, modifier = Modifier.size(28.dp)
                        )
                    }
                },
                title = { Text(text = "Archived Notes") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {

            when (uiState) {
                is UiState.Error -> {}
                UiState.Loading -> {
                    CircularProgressIndicator()
                }

                UiState.None -> {}
                is UiState.Success -> {
                    if (uiState.data.isEmpty())
                        EmptyWidget(R.drawable.outline_archive, "No Archived Notes")
                    else
                        uiState.data.map { note ->
                            NoteCard(
                                note = note,
                                onTap = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "noteId",
                                        note.id.toString()
                                    )
                                    navController.navigate(Routes.EditNoteScreen.route)
                                }
                            )
                        }
                }
            }
        }

    }

}
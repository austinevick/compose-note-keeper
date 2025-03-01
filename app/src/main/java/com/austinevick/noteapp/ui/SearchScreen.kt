package com.austinevick.noteapp.ui

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.austinevick.noteapp.R
import com.austinevick.noteapp.core.UiState
import com.austinevick.noteapp.navigation.Routes
import com.austinevick.noteapp.ui.composable.EmptyWidget
import com.austinevick.noteapp.ui.composable.InputField
import com.austinevick.noteapp.ui.composable.NoteCard
import com.austinevick.noteapp.ui.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState = viewModel.searchNoteState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(elevation = 4.dp),
                colors = TopAppBarDefaults
                    .topAppBarColors(containerColor = Color.White),
                title = {
                    InputField(
                        value = viewModel.searchQuery.value,
                        onValueChange = viewModel::onSearchQueryChange,
                        hint = "Search Notes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painterResource(R.drawable.keyboard_backspace),
                            "Back", modifier = Modifier.size(28.dp)
                        )
                    }
                })
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)) {

            when (uiState) {
                is UiState.Error -> {}
                UiState.Loading -> {}
                UiState.None -> {}
                is UiState.Success -> {
                    if (uiState.data.isEmpty())
                        EmptyWidget(R.drawable.manage_search, "No search result")
                    else
                        uiState.data.map { note ->
                            NoteCard(
                                note = note,
                                onTap = {
                                    navController.currentBackStackEntry?.
                                    savedStateHandle?.set("noteId", note.id.toString())
                                    navController.navigate(Routes.EditNoteScreen.route)
                                })
                        }
                }
            }

        }
    }


}
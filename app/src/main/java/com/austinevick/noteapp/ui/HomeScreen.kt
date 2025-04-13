package com.austinevick.noteapp.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.austinevick.noteapp.R
import com.austinevick.noteapp.core.UiState
import com.austinevick.noteapp.navigation.Routes
import com.austinevick.noteapp.theme.Green
import com.austinevick.noteapp.ui.composable.EmptyWidget
import com.austinevick.noteapp.ui.composable.NavigationDrawer
import com.austinevick.noteapp.ui.composable.NoteCard
import com.austinevick.noteapp.ui.viewmodel.MainViewModel
import com.austinevick.noteapp.ui.viewmodel.PasscodeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val uiState = viewModel.noteListState.collectAsStateWithLifecycle().value
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val activity = LocalActivity.current!!


    NavigationDrawer(navController, drawerState) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    // Clear the saved state handle before navigating
                    navController.currentBackStackEntry?.savedStateHandle?.set("noteId", null)
                    navController.navigate(Routes.EditNoteScreen.route)
                }, containerColor = Green) {
                    Icon(Icons.Default.Add, null, tint = Color.White)
                }
            },
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults
                        .topAppBarColors(containerColor = Color.White),
                    title = {
                        Text(
                            text = "Notes",
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(Routes.SearchScreen.route)
                        }) {
                            Icon(
                                Icons.Default.Search, null,
                                modifier = Modifier.size(28.dp),
                                tint = Green
                            )
                        }
                    })
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                BackHandler {
                    scope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            activity.finish()
                        }
                    }
                }

                when (uiState) {
                    is UiState.Error -> {}
                    UiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    UiState.None -> {}
                    is UiState.Success -> {
                        if (uiState.data.isEmpty())
                            EmptyWidget(R.drawable.folder_open, "No Notes Added")
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
                                    })
                            }
                    }
                }
            }

        }
    }
}
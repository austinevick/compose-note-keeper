package com.austinevick.noteapp.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.austinevick.noteapp.R
import com.austinevick.noteapp.theme.Green
import com.austinevick.noteapp.ui.composable.ConfirmDeleteModal
import com.austinevick.noteapp.ui.composable.InputField
import com.austinevick.noteapp.ui.composable.SetPasscodeModal
import com.austinevick.noteapp.ui.viewmodel.EditNoteViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavHostController,
    noteId: String? = null,
    viewModel: EditNoteViewModel = hiltViewModel()
) {

    val noteState = viewModel.noteUiState.collectAsState()
    val hasPasscode = viewModel.hasPasscode.collectAsState()
    val noteActionState = viewModel.noteActionState.collectAsStateWithLifecycle().value
    val showDeleteDialog = remember { mutableStateOf(false) }
    val showPasscodeDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }


    if (noteActionState.message != null)
        LaunchedEffect(noteActionState) {
            snackbarHostState.showSnackbar(noteActionState.message)
        }

    LaunchedEffect(noteId) {
        if (noteId != null)
            viewModel.getNote(noteId.toInt())
    }

    Log.d("NoteId", noteId.toString())

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        if (noteId == null)
                            viewModel.addNote()
                        else
                            viewModel.updateNote()
                        navController.popBackStack()
                    }) {
                        Icon(
                            painterResource(R.drawable.keyboard_backspace),
                            null, modifier = Modifier.size(28.dp)
                        )
                    }
                },
                actions = {
                  if (noteId != null)  IconButton(onClick = {
                      showDeleteDialog.value = true
                    }) {
                        Icon(
                            painterResource(R.drawable.delete_outline),
                            null,tint = Green,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    IconButton(onClick = {
                        if(hasPasscode.value){
                            showPasscodeDialog.value = true
                        }else{
                            viewModel.lockNote()
                        }
                    }) {
                        Icon(
                            painterResource(
                                if (noteState.value.isLocked) R.drawable.lock else
                                    R.drawable.lock_open
                            ),
                            null, tint = Green,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    IconButton(onClick = viewModel::pinNote) {
                        Icon(
                            painterResource(
                                if (noteState.value.isPinned) R.drawable.push_pin else
                                    R.drawable.outline_push_pin
                            ),
                            null, tint = Green,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    IconButton(onClick = viewModel::archiveNote) {
                        Icon(
                            painterResource(
                                if (noteState.value.isArchived) R.drawable.archive else
                                    R.drawable.outline_archive
                            ),
                            null, tint = Green,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        text = "Add Note",
                        fontWeight = FontWeight.SemiBold
                    )
                },
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            BackHandler {
                if (noteId == null)
                    viewModel.addNote()
                else
                    viewModel.updateNote()
                navController.popBackStack()
            }

            InputField(
                value = noteState.value.title,
                onValueChange = viewModel::updateNoteTitle,
                hint = "Title",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )

            InputField(
                value = noteState.value.content,
                onValueChange = viewModel::updateNoteContent,
                hint = "Notes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
            if (showDeleteDialog.value) {
                ConfirmDeleteModal(
                    onDismiss = {showDeleteDialog.value = false},
                    onConfirm = {
                        viewModel.deleteNote()
                        navController.popBackStack()
                    })
            }
            if(showPasscodeDialog.value){
                SetPasscodeModal(
                    onDismiss = {showPasscodeDialog.value = false}
                )
            }

        }
    }

}
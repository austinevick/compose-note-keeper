package com.austinevick.noteapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.austinevick.noteapp.ui.EditNoteScreen
import com.austinevick.noteapp.ui.ArchivedScreen
import com.austinevick.noteapp.ui.HomeScreen
import com.austinevick.noteapp.ui.LockedNoteScreen
import com.austinevick.noteapp.ui.PasscodeScreen
import com.austinevick.noteapp.ui.SearchScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val duration = 500
    NavHost(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(duration)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(duration)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(duration)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(duration)
            )
        },
        navController = navController,
        startDestination = Routes.HomeScreen.route
    ) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Routes.EditNoteScreen.route) {
            val data = navController.previousBackStackEntry?.
            savedStateHandle?.get<String>("noteId")
            EditNoteScreen(navController, noteId = data)
        }
        composable(Routes.ArchivedScreen.route) {
            ArchivedScreen(navController)
        }
        composable(Routes.SearchScreen.route) {
            SearchScreen(navController)
        }
        composable(Routes.PasscodeScreen.route) {
            PasscodeScreen(navController = navController)
        }
        composable(Routes.LockedNoteScreen.route) {
            LockedNoteScreen(navController = navController)
        }

    }
}
package com.austinevick.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.austinevick.noteapp.navigation.NavigationGraph
import com.austinevick.noteapp.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val customRippleConfiguration =
                RippleConfiguration(
                    color = Color.Gray,
                    rippleAlpha = RippleAlpha(0.8f, 0.8f, 0.8f, 0.8f)
                )
            CompositionLocalProvider(LocalRippleConfiguration provides customRippleConfiguration) {
                NoteAppTheme {
                    NavigationGraph()
                }
            }
        }
    }
}

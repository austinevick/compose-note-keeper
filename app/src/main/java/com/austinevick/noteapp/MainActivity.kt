package com.austinevick.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.austinevick.noteapp.navigation.NavigationGraph
import com.austinevick.noteapp.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var biometric: BiometricPromptManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        biometric = BiometricPromptManager(this)

        enableEdgeToEdge()
        setContent {
            val customRippleConfiguration =
                RippleConfiguration(
                    color = Color.Gray,
                    rippleAlpha = RippleAlpha(0.8f, 0.8f, 0.8f, 0.8f)
                )
            CompositionLocalProvider(
                LocalRippleConfiguration provides customRippleConfiguration) {
                NoteAppTheme {
//                    val res = biometric.promptResult.collectAsState(initial = null)
//                    Column(modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                        TextButton(onClick = {
//                            biometric.showBiometricPrompt("Authenticate", "Please authenticate to continue")
//                        }) {
//                            Text("Authenticate")
//                        }
//
//
//                        Text(text = when(res.value){
//                            is BiometricPromptManager.BiometricResult.AuthenticationError -> "Authentication Error"
//                            BiometricPromptManager.BiometricResult.AuthenticationFailed -> "Authentication Failed"
//                            BiometricPromptManager.BiometricResult.AuthenticationSuccess -> "Authentication Success"
//                            BiometricPromptManager.BiometricResult.AuthenticationNotSet -> "Authentication Not Set"
//                            BiometricPromptManager.BiometricResult.FeatureUnavailable -> "Feature Unavailable"
//                            BiometricPromptManager.BiometricResult.HardwareUnavailable -> "Hardware Unavailable"
//                            null -> "No Result"
//                        })
//
//                    }

                    NavigationGraph()
                }
            }
        }
    }
}

package com.application.scancode.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.application.scancode.presentation.screens.ConferenceScreen
import com.application.scancode.ui.theme.ScanQRCodeTheme
import com.application.scancode.viewmodel.ScanViewModel

/*
*  Tela em Kotlin Compose
*/

class ConferenceActivity: ComponentActivity() {
    private val viewModel: ScanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScanQRCodeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    ConferenceScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
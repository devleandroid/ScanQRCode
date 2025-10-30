package com.application.scancode.compose.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.application.scancode.compose.data.screens.ConferenceScreen
import com.application.scancode.compose.ui.theme.ScanQRCodeTheme
import com.application.scancode.xml.viewmodel.ScanViewModel

/*
*  Tela em Kotlin Compose
*/

class ConferenceComposeActivity: ComponentActivity() {
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
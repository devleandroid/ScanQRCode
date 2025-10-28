package com.application.scancode.presentation.screens

import android.widget.Toast
import android.window.SplashScreen
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.application.scancode.viewmodel.ScanViewModel
import com.journeyapps.barcodescanner.ScanOptions


@Composable
fun ScanQRCodeApp() {
    val navController = rememberNavController()
    val viewModel: ScanViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }

        composable("home") {
            HomeScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("conference") {
            ConferenceScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanQRCodeAppPreview() {
    ScanQRCodeApp()
}
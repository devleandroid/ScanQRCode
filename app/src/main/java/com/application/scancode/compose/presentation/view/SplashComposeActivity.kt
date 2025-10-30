package com.application.scancode.compose.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.application.scancode.compose.data.screens.SplashScreen
import com.application.scancode.compose.ui.theme.ScanQRCodeTheme


/*
*  Tela em Kotlin Compose
*/
@SuppressLint("CustomSplashScreen")
class SplashComposeActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScanQRCodeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SplashScreen(navController = navController)
                }
            }
        }
    }
}
package com.application.scancode.compose.data.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.application.scancode.compose.route.Routes
import com.application.scancode.compose.viewmodel.ScanComposeViewModel
import com.application.scancode.xml.viewmodel.ScanViewModel


@Composable
fun ScanQRCodeApp() {
    val navController = rememberNavController()
    val viewModel: ScanComposeViewModel = viewModel()
    val viewModelXml: ScanViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable(Routes.Splash) {
            SplashScreen(navController = navController)
        }

        composable(Routes.Home) {
            HomeScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Routes.Conference) {
            ConferenceScreen(
                navController = navController,
                viewModel = viewModelXml
            )
        }

        composable(Routes.Scanner) {
            QRCodeScannerScreen(
                navController = navController,
                onQRCodeScanned = { scannedValue ->
                    navController.navigate("${Routes.InvoiceDetail}?data=$scannedValue")
                }
            )
        }

        composable(
            route = "invoiceDetail?data={data}",
            arguments = listOf(navArgument("data") {
                type = NavType.StringType
            })
        ) {
            val data = it.arguments?.getString("data") ?: ""
            InvoiceDetailScreen(navController = navController, scannedData = data, viewModel = viewModel)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ScanQRCodeAppPreview() {
    ScanQRCodeApp()
}
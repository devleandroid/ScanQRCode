package com.application.scancode.compose.data.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.application.scancode.compose.data.parseQRCodeData
import com.application.scancode.compose.viewmodel.ScanComposeViewModel
import com.application.scancode.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceDetailScreen(
    navController: NavController,
    scannedData: String,
    viewModel: ScanComposeViewModel
) {
    val qrData = remember(scannedData) {
        parseQRCodeData(scannedData = scannedData)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Nota Fiscal") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") {
                        popUpTo(0) { inclusive = true }
                    } }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.green_background),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.DarkGray,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = colorResource(id = R.color.green_background)
    ) { padding ->
        qrData?.let {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                InfoItem(label = "Chave NF-e", value = it.chaveNFe)
                InfoItem(label = "Versão", value = it.versao)
                InfoItem(label = "Ambiente", value = it.ambiente)
                InfoItem(label = "ID CSC", value = it.idCSC)
                InfoItem(label = "Hash", value = it.hash)
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Falha ao ler QRCode.",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            color = Color.DarkGray,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Divider(color = Color.White.copy(alpha = 0.3f))
    }
}

@Preview(showBackground = true)
@Composable
fun InvoiceDetailScreenPreview() {
    // Dados simulados de um QRCode (mock)
    val fakeScannedData =
        "https://dfe-portal.svrs.rs.gov.br/Dfe/QrCodeNFce?p=43251093015006005930651150000774511454871020|2|1|1|C45C9B8FCA6F4E20CF08DAA7D1CDFE9D40888A6D"

    // ViewModel fake para o preview (sem Hilt)
    val fakeViewModel = remember { ScanComposeViewModel() }

   InvoiceDetailScreen(navController = NavController(LocalContext.current), scannedData = fakeScannedData, viewModel = fakeViewModel)
}

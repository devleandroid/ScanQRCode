package com.application.scancode.presentation.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.application.scancode.R
import com.application.scancode.ui.theme.GreenBackground
import com.application.scancode.ui.theme.White
import com.application.scancode.viewmodel.ScanViewModel
import com.journeyapps.barcodescanner.ScanOptions


@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ScanViewModel = viewModel()
) {
    val context = LocalContext.current

    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val scannedData = result.data?.getStringExtra("SCAN_RESULT")
            scannedData?.let { data ->
                val dataValue = data.split("|").toTypedArray()
                viewModel.setValueScanCode(dataValue)
                navController.navigate("conference") // CORRIGIDO: "conference" não "conferece"
            }
        } else {
            Toast.makeText(context, "Leitura não realizada", Toast.LENGTH_SHORT).show()
        }
    }

    // Função para lançar o scanner
    val onScanClick: () -> Unit = {
        val options = ScanOptions().apply {
            setOrientationLocked(false)
            setCameraId(0)
            setBeepEnabled(false)
            setBarcodeImageEnabled(true)
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt("Posicione o QR Code dentro do quadro")
        }
        val intent = options.createScanIntent(context)
        scannerLauncher.launch(intent)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.green_background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .width(372.dp)
                .height(89.dp)
        )

        Spacer(modifier = Modifier.height(70.dp))

        // Texto de descrição
        Text(
            text = stringResource(id = R.string.description_content),
            color = White,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Subtexto
        Text(
            text = stringResource(id = R.string.description_subtext),
            color = White,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botão de Scan
        Button(
            onClick = onScanClick,
            modifier = Modifier
                .width(300.dp)
                .height(60.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.state_layer),
                contentDescription = stringResource(R.string.scan_button),
                modifier = Modifier.size(450.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(LocalContext.current))
}
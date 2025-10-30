package com.application.scancode.compose.data.screens

import android.Manifest
import com.application.scancode.R
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.application.scancode.compose.ui.theme.White
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView


@Composable
fun QRCodeScannerScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onQRCodeScanned: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (hasCameraPermission) {
            AndroidView(
                factory = { ctx ->
                    val barcodeView = DecoratedBarcodeView(ctx).apply {
                        // Remove o texto padrao
                        setStatusText("")

                        // comwca a decodificacao continua
                        decodeContinuous(object : BarcodeCallback {
                            override fun barcodeResult(result: BarcodeResult?) {
                                result?.text?.let { scannedValue ->
                                    //Evvvita multiplas navegacoes rapidas
                                    if (scannedValue.isNotEmpty()) {
                                        pause()
                                        /*navController.navigate(
                                            "invoiceDetail?data=${scannedValue}"
                                        ) {
                                            popUpTo("scanner") { inclusive = true }
                                        }*/
                                        onQRCodeScanned(scannedValue)
                                    }
                                }
                            }

                            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {}
                        })
                        resume()
                    }
                    barcodeView
                },
                update = { view ->
                    view.resume()
                },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = "Permissão de câmera negada. Ative-a para usar o leitor de QRCode.",
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        //🔙 Botão Fechar (volta para a Home)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 20.dp)
        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .width(102.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, Color.White),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_fechar),
                    contentDescription = stringResource(id = R.string.btnClose),
                    modifier = Modifier.size(120.dp),
                    tint = White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QRCodeScannerScreenPreview() {
    QRCodeScannerScreen(navController = NavController(LocalContext.current))
}
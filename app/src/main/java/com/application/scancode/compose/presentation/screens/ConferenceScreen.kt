package com.application.scancode.compose.data.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.application.scancode.compose.ui.theme.White
import com.application.scancode.R
import com.application.scancode.xml.viewmodel.ScanViewModel


@Composable
fun ConferenceScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ScanViewModel = viewModel()
) {
    val qrCodeData by viewModel.qrCode.collectAsState()

    // Estados para os campos com máscaras aplicadas
    var cnpjText by remember { mutableStateOf("") }
    var keyText by remember { mutableStateOf("") }
    var valueText by remember { mutableStateOf("") }

    // Aplicar máscaras quando os dados do ViewModel mudarem
    LaunchedEffect(qrCodeData) {
        qrCodeData?.let { data ->
            cnpjText = applyCnpjMask(data.cnpj)
            keyText = applyKeyMask(data.key)
            valueText = applyMonetaryMask(data.valuePrice)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.green_background))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botão Fechar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    viewModel.clearData()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .width(102.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_fechar),
                    contentDescription = stringResource(id = R.string.btnClose),
                    modifier = Modifier.size(120.dp),
                    tint = White
                )
            }
        }

        // Texto de descrição
        Text(
            text = stringResource(id = R.string.description_conf_info),
            color = White,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        )

        // Seção CNPJ
        ConferenceFieldSection(
            title = stringResource(id = R.string.cnpj),
            value = cnpjText,
            modifier = Modifier.padding(top = 70.dp)
        )

        // Seção Chave
        ConferenceFieldSection(
            title = stringResource(id = R.string.key),
            value = keyText,
            modifier = Modifier.padding(top = 70.dp),
            multiLine = true
        )

        // Seção Valor
        ConferenceFieldSection(
            title = stringResource(id = R.string.value),
            value = valueText,
            modifier = Modifier.padding(top = 70.dp)
        )
    }
}

@Composable
private fun ConferenceFieldSection(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    multiLine: Boolean = false
) {
    Column(
        modifier = modifier.height(120.dp)
    ) {
        // Título
        Text(
            text = title,
            color = White,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo de texto (readonly)
        OutlinedTextField(
            value = value,
            onValueChange = { }, // Readonly
            modifier = Modifier
                .fillMaxWidth()
                .height(if (multiLine) 70.dp else 48.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = White),
            readOnly = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedTextColor = White,
                focusedTextColor = White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = White
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Linha divisória
        Divider(
            color = White,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Funções de máscara
private fun applyCnpjMask(cnpj: String): String {
    return if (cnpj.length == 14) {
        "${cnpj.substring(0, 2)}.${cnpj.substring(2, 5)}.${cnpj.substring(5, 8)}/${cnpj.substring(8, 12)}-${cnpj.substring(12)}"
    } else {
        cnpj
    }
}

private fun applyKeyMask(key: String): String {
    return key.chunked(4).joinToString(" ")
}

private fun applyMonetaryMask(value: String): String {
    return try {
        val numericValue = value.replace("[^\\d.]".toRegex(), "")
        if (numericValue.isNotEmpty()) {
            val amount = numericValue.toDouble()
            "R$ ${String.format("%.2f", amount).replace('.', ',')}"
        } else {
            "R$ 0,00"
        }
    } catch (e: Exception) {
        "R$ $value"
    }
}

@Preview(showBackground = true)
@Composable
fun ConferenceScreenPreview() {
    ConferenceScreen(navController = NavController(LocalContext.current))
}
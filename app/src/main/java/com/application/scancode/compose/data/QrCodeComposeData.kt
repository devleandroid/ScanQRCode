package com.application.scancode.compose.data

data class QrCodingData(
    val chaveNFe: String,
    val versao: String,
    val ambiente: String,
    val idCSC: String,
    val hash: String
)

fun parseQRCodeData(scannedData: String): QrCodingData? {
    return try {
        val query = scannedData.substringAfter("p=")
        val parts = query.split("|")

        QrCodingData(
            chaveNFe = parts.getOrNull(0) ?: "",
            versao = parts.getOrNull(1) ?: "",
            ambiente = parts.getOrNull(2) ?: "",
            idCSC = parts.getOrNull(3) ?: "",
            hash = parts.getOrNull(4) ?: ""
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
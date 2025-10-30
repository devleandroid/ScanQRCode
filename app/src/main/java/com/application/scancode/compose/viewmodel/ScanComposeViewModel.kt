package com.application.scancode.compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.scancode.compose.data.QrCodingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScanComposeViewModel : ViewModel() {
    private val _qrCode = MutableStateFlow<QrCodingData?>(null)
    val qrCode: StateFlow<QrCodingData?> = _qrCode.asStateFlow()

    fun setValueScanCode(value: Array<String>) {
        viewModelScope.launch {
            _qrCode.value = QrCodingData(
                chaveNFe = value.getOrElse(0) { "" },
                versao = value.getOrElse(1) { "" },
                ambiente = value.getOrElse(2) { "" },
                idCSC = value.getOrElse(3) { "" },
                hash = value.getOrElse(4) { "" },
            )

            getValueScancode()
        }
    }

    private val _valueQRCode = MutableLiveData<QrCodingData>()

    var qrCodeData: LiveData<QrCodingData> = _valueQRCode

    fun getValueScancode() {
        val qrCode = QrCodingData(
            chaveNFe = _qrCode.value?.chaveNFe ?: "",
            versao = _qrCode.value?.versao ?: "",
            ambiente = _qrCode.value?.ambiente ?: "",
            idCSC = _qrCode.value?.idCSC ?: "",
            hash = _qrCode.value?.hash ?: ""
        )
        return _valueQRCode.postValue(qrCode)
    }

    fun clearData() {
        viewModelScope.launch { _qrCode.value = null }
    }
}
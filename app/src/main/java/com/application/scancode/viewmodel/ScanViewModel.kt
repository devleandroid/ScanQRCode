package com.application.scancode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.scancode.data.model.QrCodeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScanViewModel : ViewModel() {
    private val _qrCode = MutableStateFlow<QrCodeData?>(null)
    val qrCode: StateFlow<QrCodeData?> = _qrCode.asStateFlow()

    fun setValueScanCode(value: Array<String>) {
        viewModelScope.launch {
            _qrCode.value = QrCodeData(
                cnpj = value.getOrElse(0) {""},
                key = value.getOrElse(1) {""},
                valuePrice = value.getOrElse(2) {""}
            )

            getValueScancode()
        }
    }

    private val _valueQRCode = MutableLiveData<QrCodeData>()

    var qrCodes: LiveData<QrCodeData> = _valueQRCode

    fun getValueScancode() {
        val qrCode = QrCodeData(
            cnpj = _qrCode.value?.cnpj ?: "",
            key = _qrCode.value?.key ?: "",
            valuePrice = _qrCode.value?.valuePrice ?: ""
        )
        return _valueQRCode.postValue(qrCode)
    }

    fun clearData() {
        viewModelScope.launch { _qrCode.value = null }
    }
}
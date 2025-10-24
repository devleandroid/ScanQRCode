package com.application.scancode.viewmodel

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

            //getValueScancode()
        }
    }

    val valueLiveData = MutableLiveData<Array<String>>()

    private val _valueQRCode = MutableLiveData<Array<QrCodeData?>>()

    var qrCodes = _valueQRCode

    /*fun getValueScancode() {
        viewModelScope.launch {
            qrCode.apply {
                cnpj = valueLiveData.value?.get(0).toString()
                key = valueLiveData.value?.get(1).toString()
                valuePrice = valueLiveData.value?.get(2).toString()
            }
        }
        return valueQRCode.postValue(arrayOf(qrCode))
    }*/

    fun clearData() {
        viewModelScope.launch { _qrCode.value = null }
    }
}
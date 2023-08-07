package com.application.scancode.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.scancode.data.model.QRCode
import kotlinx.coroutines.launch

class ScanViewModel : ViewModel() {

    val valueLiveData = MutableLiveData<Array<String>>()

    private val valueQRCode = MutableLiveData<Array<QRCode>>()

    var qrCode = QRCode("","","")

    fun setValueScanCode(value: Array<String>) {
        viewModelScope.launch {
            valueLiveData.value = value

            getValueScancode()
        }
    }

    fun getValueScancode() {
        viewModelScope.launch {
            qrCode.apply {
                cnpj = valueLiveData.value?.get(0).toString()
                key = valueLiveData.value?.get(1).toString()
                valuePrice = valueLiveData.value?.get(2).toString()
            }
        }
        return valueQRCode.postValue(arrayOf(qrCode))
    }

}
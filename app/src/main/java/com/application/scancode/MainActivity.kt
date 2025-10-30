package com.application.scancode

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.application.scancode.compose.data.screens.ScanQRCodeApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.IS_COMPOSE) {
            setContent {
                ScanQRCodeApp()
            }
        } else {
            setContentView(R.layout.activity_main)
        }
    }
}
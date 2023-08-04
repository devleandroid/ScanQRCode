package com.application.scancode.presentation.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.application.scancode.R
import com.application.scancode.databinding.FragmentReadQrcodeBinding
import com.journeyapps.barcodescanner.CaptureManager


/**
 * Tela responsavel por realizar a leitura do QRCode.
 * Nela contem binding configurado no gradle.
 * Ela realiza a configuralçao da camera e do formato de leitura do codigo.
 */

class ReadQRCodeActivity : AppCompatActivity() {
    private val binding by lazy {
        FragmentReadQrcodeBinding.inflate(layoutInflater)
    }
    private lateinit var capture: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        initializeQrScanner(savedInstanceState)
        setOnClickListener()
    }

    private fun initializeQrScanner(savedInstanceState: Bundle?) = with(binding) {
        capture = CaptureManager(this@ReadQRCodeActivity, zxingBarcodeScanner)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.setShowMissingCameraPermissionDialog(false)
        capture.decode()
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return binding.zxingBarcodeScanner.onKeyDown(keyCode, event) || super.onKeyDown(
            keyCode,
            event
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setOnClickListener() {
        findViewById<ImageButton>(R.id.btn_close_scan).setOnClickListener {
            finish()
        }
    }
}
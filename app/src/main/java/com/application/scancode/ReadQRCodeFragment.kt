package com.application.scancode

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.application.scancode.databinding.FragmentReadQrcodeBinding
import com.google.zxing.ResultPoint
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.camera.CameraSettings
import org.json.JSONException
import org.json.JSONObject


class ReadQRCodeFragment : Fragment() {
    private lateinit var binding: FragmentReadQrcodeBinding
    private lateinit var qrScanIntegrator: IntentIntegrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadQrcodeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val qrView: DecoratedBarcodeView = binding.qrScannerView
        val s = CameraSettings()
        s.requestedCameraId = 1 // front/back/etc

        qrView.barcodeView.cameraSettings = s
        qrView.resume()

        qrView.decodeSingle(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                Log.d("Leitura do codigo","barcode result: $result")
                // do your thing with result
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })

        setupScanner()
        setOnClickListener(view)
    }

    private fun setupScanner() {
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
        qrScanIntegrator.setPrompt("Scan a barcode or QR Code")
        qrScanIntegrator.setOrientationLocked(true)
        qrScanIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(activity, com.application.scancode.R.string.result_not_found, Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = JSONObject(result.contents)

                    // Show values in UI.
//                    binding.name.text = obj.getString("name")
//                    binding.siteName.text = obj.getString("site_name")

                } catch (e: JSONException) {
                    e.printStackTrace()
                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setOnClickListener(view: View) {
        binding.btnClose.setOnClickListener {
            val action = ReadQRCodeFragmentDirections.actionReadQRCodeFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
    }

    companion object {

    }
}
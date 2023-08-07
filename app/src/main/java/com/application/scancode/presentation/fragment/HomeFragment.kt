package com.application.scancode.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.application.scancode.databinding.FragmentHomeBinding
import com.application.scancode.presentation.activity.ReadQRCodeActivity
import com.application.scancode.viewmodel.ScanViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

/**
 * Tela inicial do aplicativo
 * Ela contem a configuração inicial para o leitura do QRCode
 * Processa os dados lido pela camera e repassa esses dados para a tela de ConferenceFragment
 * via navigation
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ScanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(activity!!)[ScanViewModel::class.java]
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnScan.setOnClickListener {
            launchScanner()
            onClick()
        }
    }

    private fun onClick() {
        val intent = Intent(requireContext(), ReadQRCodeActivity::class.java)
        startActivity(intent)
    }

    private fun launchScanner() {
        val options = ScanOptions()
            .setOrientationLocked(false)
            .setCaptureActivity(ReadQRCodeActivity::class.java)
            .setCameraId(0)
            .setBeepEnabled(false)
            .setBarcodeImageEnabled(true)
            .setDesiredBarcodeFormats(ScanOptions.QR_CODE)

        barcodeLauncher.launch(options)
    }

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (!result.contents.isNullOrEmpty()) {
            val dataValue = result.contents.split("|").toTypedArray()
            viewModel.setValueScanCode(dataValue)
            val action = HomeFragmentDirections.actionHomeFragmentToConferenceFragment()
            findNavController().navigate(action)
        } else {
            // CANCELED
            // Valores usados para testar no emulador
            // val dataValue = "33511889000120|32191105570714000825550010059146621133082968|190.00".split("|").toTypedArray()
            // viewModel.setValueScanCode(dataValue)
            // val action = HomeFragmentDirections.actionHomeFragmentToConferenceFragment()
            // findNavController().navigate(action)
            Toast.makeText(activity, "Leitura não realizada.", Toast.LENGTH_SHORT).show()
        }
    }

}
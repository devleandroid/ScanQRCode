package com.application.scancode.xml.viewmodel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.application.scancode.databinding.FragmentConferenceBinding
import com.application.scancode.xml.utils.MaskEditUtil
import com.application.scancode.xml.viewmodel.ScanViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch


/**
 * Tela para mostrar os resultados lido no QRCode.
 * Ela contem os campos com as mascaras de CNPJ, Chave e Valor
 *
 */
class ConferenceFragment : Fragment() {

    private lateinit var binding: FragmentConferenceBinding

    private val viewModel: ScanViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConferenceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // val viewModel = ViewModelProvider(requireActivity())[ScanViewModel::class.java]

        binding.edtCnpj.addTextChangedListener(MaskEditUtil.mask(binding.edtCnpj, MaskEditUtil.FORMAT_CNPJ))

        binding.edtKey.addTextChangedListener(MaskEditUtil.mask(binding.edtKey, MaskEditUtil.FORMAT_KEYVALUE))

        binding.edtValue.addTextChangedListener(MaskEditUtil.mask(binding.edtValue, MaskEditUtil.FRMAT_MONETARY))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.qrCode.collect {
                    binding.edtCnpj.setText(it?.cnpj)
                    binding.edtKey.setText(it?.key)
                    binding.edtValue.setText(it?.valuePrice)
                }
            }
        }

        setOnClickListener(view)
    }

    private fun setOnClickListener(view: View) {
        binding.btnClose.setOnClickListener {
            val action = ConferenceFragmentDirections.actionConferenceFragmentToHomeFragment().apply {
                exitTransition = MaterialSharedAxis(
                    MaterialSharedAxis.Z,
                    /* forward= */ true
                ).apply {
                    duration = 2000
                }
            }
            view.findNavController().navigate(action)
        }
    }
}
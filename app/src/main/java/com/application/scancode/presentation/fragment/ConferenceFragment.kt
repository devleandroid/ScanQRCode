package com.application.scancode.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.application.scancode.databinding.FragmentConferenceBinding
import com.application.scancode.utils.MaskEditUtil


/**
 * Tela para mostrar os resultados lido no QRCode.
 * Ela contem os campos com as mascaras de CNPJ, Chave e Valor
 *
 */
class ConferenceFragment : Fragment() {

    private lateinit var binding: FragmentConferenceBinding
    private val args: ConferenceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments = args.toBundle()
        binding = FragmentConferenceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtCnpj.addTextChangedListener(MaskEditUtil.mask(binding.edtCnpj, MaskEditUtil.FORMAT_CNPJ))
        binding.edtCnpj.setText(args.value[0])

        binding.edtKey.addTextChangedListener(MaskEditUtil.mask(binding.edtKey, MaskEditUtil.FORMAT_KEYVALUE))
        binding.edtKey.setText(args.value[1])

        binding.edtValue.addTextChangedListener(MaskEditUtil.mask(binding.edtValue, MaskEditUtil.FRMAT_MONETARY))
        binding.edtValue.setText(args.value[2])
        setOnClickListener(view)
    }

    private fun setOnClickListener(view: View) {
        binding.btnClose.setOnClickListener {
            val action = ConferenceFragmentDirections.actionConferenceFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
    }
}
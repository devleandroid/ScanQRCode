package com.application.scancode.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.scancode.R
import com.google.android.material.transition.MaterialSharedAxis

/**
 * Tela de apresentação do aplicativo
 * Nela contem o logo
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backgroundImage: ImageView = view.findViewById(R.id.img_splash)
        val slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.side_splash)
        backgroundImage.startAnimation(slideAnimation)


        Handler(Looper.getMainLooper()).postDelayed({
            val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment().apply {
                exitTransition = MaterialSharedAxis(
                    MaterialSharedAxis.Z,
                    /* forward= */ true
                ).apply {
                    duration = 2000
                }
            }

            findNavController().navigate(action)
            activity?.fragmentManager?.popBackStack()
        }, 3000)
    }
}
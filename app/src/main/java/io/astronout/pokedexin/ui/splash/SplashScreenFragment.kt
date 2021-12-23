package io.astronout.pokedexin.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.astronout.pokedexin.R
import io.astronout.pokedexin.databinding.FragmentSplashScreenBinding
import io.astronout.pokedexin.utils.delayJob
import io.astronout.pokedexin.utils.wait

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    private val binding: FragmentSplashScreenBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wait(5000) {
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment())
        }
    }

}
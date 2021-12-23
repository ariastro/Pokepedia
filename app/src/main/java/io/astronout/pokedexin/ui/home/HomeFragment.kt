package io.astronout.pokedexin.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import com.kennyc.view.MultiStateView
import io.astronout.pokedexin.R
import io.astronout.pokedexin.databinding.FragmentHomeBinding
import io.astronout.pokedexin.ui.home.adapter.PokemonAdapter
import io.astronout.pokedexin.utils.wait

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val adapter = PokemonAdapter(onClickListener = {

    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

    }

    private fun setupUI() {
        with(binding) {
            wait(10000) {
                msvPokemon.viewState = MultiStateView.ViewState.CONTENT
            }
            rvPokemon.adapter = adapter
        }
    }

}
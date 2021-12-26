package io.astronout.pokepedia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import com.kennyc.view.MultiStateView
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentHomeBinding
import io.astronout.pokepedia.ui.home.adapter.PokemonAdapter
import io.astronout.pokepedia.utils.wait

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
            wait(2000) {
                msvPokemon.viewState = MultiStateView.ViewState.CONTENT
            }
            rvPokemon.adapter = adapter
        }
    }

}
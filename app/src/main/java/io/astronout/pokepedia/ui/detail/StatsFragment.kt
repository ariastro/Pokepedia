package io.astronout.pokepedia.ui.detail

import android.viewbinding.library.fragment.viewBinding
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentStatsBinding
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.ui.detail.adapter.PokemonStatsAdapter

class StatsFragment : BaseFragment(R.layout.fragment_stats) {

    private val binding: FragmentStatsBinding by viewBinding()
    private val adapter = PokemonStatsAdapter()
    private val dummyStats = listOf(
        Pokemon.Stats(45, 0, "hp"),
        Pokemon.Stats(49, 0, "attack"),
        Pokemon.Stats(65, 0, "special-attack"),
        Pokemon.Stats(65, 0, "special-defense"),
        Pokemon.Stats(45, 0, "speed")
    )

    override fun initData() {
        adapter.submitList(dummyStats)
    }

    override fun initUI() {
        with(binding) {
            rvPokemonStats.adapter = adapter
        }
    }

    override fun initAction() {
        // do nothing
    }

    override fun initObserver() {
        // do nothing
    }

}
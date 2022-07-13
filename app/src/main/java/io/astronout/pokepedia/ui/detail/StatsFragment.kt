package io.astronout.pokepedia.ui.detail

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.activityViewModels
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentStatsBinding
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.ui.detail.adapter.PokemonStatsAdapter
import io.astronout.pokepedia.ui.detail.viewmodel.DetailViewModel
import io.astronout.pokepedia.utils.*
import io.astronout.pokepedia.vo.Resource

class StatsFragment : BaseFragment(R.layout.fragment_stats) {

    private val binding: FragmentStatsBinding by viewBinding()
    private val viewModel: DetailViewModel by activityViewModels()
    private val adapter = PokemonStatsAdapter()

    override fun initUI() {
        with(binding) {
            rvPokemonStats.adapter = adapter
        }
    }

    override fun initObserver() {
        collectLifecycleFlow(viewModel.getPokemonDetails()) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message.toString())
                }
                is Resource.Loading -> binding.msvPokemonStats.showLoadingLayout()
                is Resource.Success -> {
                    it.data?.let { pokemon ->
                        pokemon.types.firstOrNull()?.let { type ->
                            with(binding) {
                                tvBaseStats.setTextColorResource(type.name.getPokemonTypeColor())
                                msvPokemonStats.showDefaultLayout()
                            }
                        }
                        adapter.submitList(pokemon.stats)
                    }
                }
            }
        }
    }

}
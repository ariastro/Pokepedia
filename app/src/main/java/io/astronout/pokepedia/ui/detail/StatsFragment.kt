package io.astronout.pokepedia.ui.detail

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.activityViewModels
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentStatsBinding
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.ui.detail.adapter.PokemonStatsAdapter
import io.astronout.pokepedia.ui.detail.viewmodel.DetailViewModel
import io.astronout.pokepedia.utils.collectLifecycleFlow
import io.astronout.pokepedia.utils.getPokemonTypeColor
import io.astronout.pokepedia.utils.setTextColorResource
import io.astronout.pokepedia.utils.showToast
import io.astronout.pokepedia.vo.Resource

class StatsFragment : BaseFragment(R.layout.fragment_stats) {

    private val binding: FragmentStatsBinding by viewBinding()
    private val viewModel: DetailViewModel by activityViewModels()
    private val adapter = PokemonStatsAdapter()

    override fun initData() {
        // do nothing
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
        collectLifecycleFlow(viewModel.getPokemonDetails()) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message.toString())
                }
                is Resource.Loading -> showToast("Loading")
                is Resource.Success -> {
                    it.data?.let { pokemon ->
                        adapter.submitList(pokemon.stats)
                        pokemon.types.firstOrNull()?.let { type ->
                            with(binding) {
                                tvBaseStats.setTextColorResource(type.name.getPokemonTypeColor())
                            }
                        }
                    }
                }
            }
        }
    }

}
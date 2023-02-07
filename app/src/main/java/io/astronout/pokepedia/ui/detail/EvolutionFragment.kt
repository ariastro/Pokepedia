package io.astronout.pokepedia.ui.detail

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentEvolutionBinding
import io.astronout.pokepedia.di.GlideApp
import io.astronout.pokepedia.domain.model.PokemonEvolution
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.ui.detail.viewmodel.DetailViewModel
import io.astronout.pokepedia.utils.*
import io.astronout.pokepedia.vo.Resource

class EvolutionFragment : BaseFragment(R.layout.fragment_evolution) {

    private val binding: FragmentEvolutionBinding by viewBinding()
    private val viewModel: DetailViewModel by activityViewModels()

    override fun initData() {
        collectLifecycleFlow(viewModel.getPokemonSpecies()) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message)
                }
                is Resource.Loading -> {
//                    binding.msvAbout.showLoadingLayout()
                }
                is Resource.Success -> {
                    getPokemonEvolution(it.data.evolutionChainId)
                }
            }
        }
    }

    private fun getPokemonEvolution(evolutionId: Int) {
        collectLifecycleFlow(viewModel.getPokemonEvolution(evolutionId)) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message)
                }
                is Resource.Loading -> {
//                    binding.msvAbout.showLoadingLayout()
                }
                is Resource.Success -> {
//                    binding.msvAbout.showDefaultLayout()
                    showPokemonEvolution(it.data)
                }
            }
        }
    }

    private fun showPokemonEvolution(data: PokemonEvolution) {
        with(binding) {
            tvFirstPokemonIndex.text = data.chain.species.url.getPokemonId().getPokemonIndex()
            tvFirstPokemonName.text = data.chain.species.name.capitalize()
            GlideApp.with(this@EvolutionFragment)
                .load(data.chain.species.url.getPokemonId().getPokemonImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivFirstEvolution)
            tvSecondPokemonIndex.text = data.chain.evolvesTo.firstOrNull()?.species?.url?.getPokemonId()?.getPokemonIndex()
            tvSecondPokemonName.text = data.chain.evolvesTo.firstOrNull()?.species?.name?.capitalize()
            GlideApp.with(this@EvolutionFragment)
                .load(data.chain.evolvesTo.firstOrNull()?.species?.url?.getPokemonId()?.getPokemonImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivSecondEvolution)
            tvThirdPokemonIndex.text = data.chain.evolvesTo.firstOrNull()?.species?.url?.getPokemonId()?.getPokemonIndex()
            tvThirdPokemonName.text = data.chain.evolvesTo.firstOrNull()?.species?.name?.capitalize()
            GlideApp.with(this@EvolutionFragment)
                .load(data.chain.evolvesTo.firstOrNull()?.species?.url?.getPokemonId()?.getPokemonImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivThirdEvolution)
            tvFourthPokemonIndex.text = data.chain.evolvesTo.firstOrNull()?.evolvesTo?.firstOrNull()?.species?.url?.getPokemonId()?.getPokemonIndex()
            tvFourthPokemonName.text = data.chain.evolvesTo.firstOrNull()?.evolvesTo?.firstOrNull()?.species?.name?.capitalize()
            GlideApp.with(this@EvolutionFragment)
                .load(data.chain.evolvesTo.firstOrNull()?.evolvesTo?.firstOrNull()?.species?.url?.getPokemonId()?.getPokemonImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivFourthEvolution)
        }
    }

}
package io.astronout.pokepedia.ui.detail

import android.annotation.SuppressLint
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.activityViewModels
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentAboutBinding
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.domain.model.PokemonSpecies
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.ui.detail.viewmodel.DetailViewModel
import io.astronout.pokepedia.utils.*
import io.astronout.pokepedia.vo.Resource

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    private val binding: FragmentAboutBinding by viewBinding()
    private val viewModel: DetailViewModel by activityViewModels()

    override fun initObserver() {
        collectLifecycleFlow(viewModel.getPokemonDetails()) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message.toString())
                }
                is Resource.Loading -> binding.msvAbout.showLoadingLayout()
                is Resource.Success -> {
                    it.data?.let { pokemon ->
                        showPokemonDetails(pokemon)
                    }
                }
            }
        }

        collectLifecycleFlow(viewModel.getPokemonSpecies()) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message.toString())
                }
                is Resource.Loading -> binding.msvAbout.showLoadingLayout()
                is Resource.Success -> {
                    it.data?.let { pokemonSpecies ->
                        showPokemonSpecies(pokemonSpecies)
                    }
                    binding.msvAbout.showDefaultLayout()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showPokemonDetails(pokemon: Pokemon) {
        with(binding) {
            pokemon.types.firstOrNull()?.let { type ->
                tvPokedexData.setTextColorResource(type.name.getPokemonTypeColor())
                tvTraining.setTextColorResource(type.name.getPokemonTypeColor())
                tvBreeding.setTextColorResource(type.name.getPokemonTypeColor())
            }
            tvHeight.text = "${pokemon.height.toDouble() / 10}m"
            tvWeight.text = "${pokemon.weight.toDouble() / 10}kg"
            tvAbilities.text = pokemon.abilities.joinToString { ability ->
                if (ability.isHidden) {
                    "${ability.name.capitalize()} (hidden ability)"
                } else {
                    ability.name.capitalize()
                }
            }
            tvBaseExperience.text = pokemon.baseExperience.toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showPokemonSpecies(pokemonSpecies: PokemonSpecies) {
        with(binding) {
            tvFlavorText.text = pokemonSpecies.flavorTextEntries.firstOrNull { it.language == "en" }?.flavorText?.replace("\n", " ").orEmpty()
            tvSpecies.text = pokemonSpecies.genera.firstOrNull { it.language == "en" }?.genus?.capitalize().orEmpty()
            tvCatchRate.text = "${pokemonSpecies.captureRate}%"
            tvBaseFriendship.text = pokemonSpecies.baseHappiness.toString()
            tvGrowthRate.text = pokemonSpecies.growthRate.capitalize()
            tvHabitat.text = pokemonSpecies.habitat.capitalize()
            val genderRate = pokemonSpecies.genderRate.toGenderPercentage()
            tvGender.apply {
                text = ""
                append(requireContext().getColoredString("♂ ${genderRate.first}%, ", R.color.height_medium))
                append(requireContext().getColoredString("♀ ${genderRate.second}%", R.color.height_short))
            }
            tvEggGroup.text = pokemonSpecies.eggGroups.joinToString { eggGroup -> eggGroup.capitalize() }
            tvEggCycles.text = pokemonSpecies.hatchCounter.toString()
        }
    }

    private fun Int.toGenderPercentage(): Pair<Double, Double> {
        val female = (this.toDouble() / FEMALE_PERCENTAGE) * 100
        val male = 100 - female
        return Pair(male, female)
    }

    companion object {
        const val FEMALE_PERCENTAGE = 8
    }

}
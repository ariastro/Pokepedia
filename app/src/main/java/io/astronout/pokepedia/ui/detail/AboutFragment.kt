package io.astronout.pokepedia.ui.detail

import android.annotation.SuppressLint
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.activityViewModels
import io.astronout.pokepedia.R
import io.astronout.pokepedia.databinding.FragmentAboutBinding
import io.astronout.pokepedia.ui.base.BaseFragment
import io.astronout.pokepedia.ui.detail.viewmodel.DetailViewModel
import io.astronout.pokepedia.utils.capitalize
import io.astronout.pokepedia.utils.collectLifecycleFlow
import io.astronout.pokepedia.utils.getColoredString
import io.astronout.pokepedia.utils.showToast
import io.astronout.pokepedia.vo.Resource

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    private val binding: FragmentAboutBinding by viewBinding()
    private val viewModel: DetailViewModel by activityViewModels()

    override fun initData() {
        // do nothing
    }

    override fun initUI() {
        // do nothing
    }

    override fun initAction() {
        // do nothing
    }

    @SuppressLint("SetTextI18n")
    override fun initObserver() {
        collectLifecycleFlow(viewModel.getPokemonDetails()) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message.toString())
                }
                is Resource.Loading -> showToast("Loading")
                is Resource.Success -> {
                    with(binding) {
                        it.data?.let { pokemon ->
                            tvHeight.text = "${pokemon.height / 10}m"
                            tvWeight.text = "${pokemon.weight / 10}kg"
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
                }
            }
        }

        collectLifecycleFlow(viewModel.getPokemonSpecies()) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message.toString())
                }
                is Resource.Loading -> showToast("Loading")
                is Resource.Success -> {
                    with(binding) {
                        it.data?.let { pokemonSpecies ->
                            tvFlavorText.text = pokemonSpecies.flavorTextEntries.firstOrNull { flavorTextEntry -> flavorTextEntry.language == "en" }?.flavorText?.replace("\n", " ") ?: ""
                            tvSpecies.text = pokemonSpecies.genera.firstOrNull { genera -> genera.language == "en" }?.genus?.capitalize() ?: ""
                            tvCatchRate.text = "${pokemonSpecies.captureRate}%"
                            tvBaseFriendship.text = pokemonSpecies.baseHappiness.toString()
                            tvGrowthRate.text = pokemonSpecies.growthRate.capitalize()
                            tvHabitat.text = pokemonSpecies.habitat.capitalize()
                            val genderRate = pokemonSpecies.genderRate.toGenderPercentage()
                            tvGender.append(requireContext().getColoredString("♂ ${genderRate.first}%, ", R.color.height_medium))
                            tvGender.append(requireContext().getColoredString("♀ ${genderRate.second}%", R.color.height_short))
                            tvEggGroup.text = pokemonSpecies.eggGroups.joinToString { eggGroup -> eggGroup.capitalize() }
                            tvEggCycles.text = pokemonSpecies.hatchCounter.toString()
                        }
                    }
                }
            }
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
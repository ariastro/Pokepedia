package io.astronout.pokepedia.ui.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.astronout.pokepedia.domain.repository.PokepediaRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val pokepediaRepository: PokepediaRepository): ViewModel() {

    fun getPokemonDetails(id: Int) = pokepediaRepository.getPokemonDetails(id)

}
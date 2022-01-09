package io.astronout.pokepedia.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.astronout.pokepedia.domain.repository.PokepediaRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val pokepediaRepository: PokepediaRepository): ViewModel() {

    private val _pokemonId = MutableLiveData<Int>()
    val pokemonId: LiveData<Int> = _pokemonId

    fun setPokemonId(id: Int) {
        _pokemonId.value = id
    }

    fun getPokemonDetails() = pokepediaRepository.getPokemonDetails(pokemonId.value ?: 0)

    fun getPokemonSpecies() = pokepediaRepository.getPokemonSpecies(pokemonId.value ?: 0)

}
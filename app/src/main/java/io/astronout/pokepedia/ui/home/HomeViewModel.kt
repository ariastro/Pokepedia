package io.astronout.pokepedia.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.astronout.pokepedia.domain.repository.PokepediaRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(pokepediaRepository: PokepediaRepository): ViewModel() {

    val pokemonList = pokepediaRepository.getAllPokemons()
        .cachedIn(viewModelScope)

}
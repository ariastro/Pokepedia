package io.astronout.pokepedia.domain.repository

import androidx.paging.PagingData
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.domain.model.PokemonEvolution
import io.astronout.pokepedia.domain.model.PokemonSpecies
import io.astronout.pokepedia.vo.Resource
import kotlinx.coroutines.flow.Flow

interface PokepediaRepository {

    fun getAllPokemons(): Flow<PagingData<Pokemon>>

    fun getPokemonDetails(id: Int): Flow<Resource<Pokemon>>

    fun getPokemonSpecies(id: Int): Flow<Resource<PokemonSpecies>>

    fun getPokemonEvolution(id: Int): Flow<Resource<PokemonEvolution>>

}
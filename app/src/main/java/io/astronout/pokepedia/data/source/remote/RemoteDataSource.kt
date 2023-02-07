package io.astronout.pokepedia.data.source.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val pokepediaService: PokepediaService) {

    suspend fun getAllPokemons(limit: Int, offset: Int) = pokepediaService.getAllPokemons(limit, offset)

    suspend fun getPokemonDetails(id: Int) = pokepediaService.getPokemonDetails(id)

    suspend fun getPokemonSpecies(id: Int) = pokepediaService.getPokemonSpecies(id)

    suspend fun getPokemonEvolution(id: Int) = pokepediaService.getPokemonEvolution(id)

}
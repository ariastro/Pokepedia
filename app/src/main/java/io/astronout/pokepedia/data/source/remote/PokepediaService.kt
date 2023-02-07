package io.astronout.pokepedia.data.source.remote

import com.skydoves.sandwich.ApiResponse
import io.astronout.pokepedia.data.source.remote.response.PokemonDetailResponse
import io.astronout.pokepedia.data.source.remote.response.PokemonEvolutionResponse
import io.astronout.pokepedia.data.source.remote.response.PokemonResponse
import io.astronout.pokepedia.data.source.remote.response.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokepediaService {

    @GET("pokemon")
    suspend fun getAllPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): ApiResponse<PokemonDetailResponse>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): ApiResponse<PokemonSpeciesResponse>

    @GET("evolution-chain/{id}")
    suspend fun getPokemonEvolution(@Path("id") id: Int): ApiResponse<PokemonEvolutionResponse>

}
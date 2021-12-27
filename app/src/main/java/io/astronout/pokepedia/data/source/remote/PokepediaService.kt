package io.astronout.pokepedia.data.source.remote

import com.skydoves.sandwich.ApiResponse
import io.astronout.pokepedia.data.source.remote.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokepediaService {

    @GET("pokemon")
    suspend fun getAllPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonResponse

}
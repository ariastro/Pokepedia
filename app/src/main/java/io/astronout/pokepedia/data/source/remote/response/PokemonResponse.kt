package io.astronout.pokepedia.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.utils.getPokemonId
import io.astronout.pokepedia.utils.getPokemonImage

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "next") val next: String? = null,
    @field:Json(name = "previous") val previous: String? = null,
    @field:Json(name = "results") val results: List<Result> = emptyList()
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        @field:Json(name = "name") val name: String,
        @field:Json(name = "url") val url: String
    ) {
        fun toPokemon(): Pokemon {
            return Pokemon(
                id = url.getPokemonId(),
                name = name,
                image = url.getPokemonImage()
            )
        }
    }
}
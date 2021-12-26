package io.astronout.pokepedia.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @Json(name = "count") val count: Int? = null,
    @Json(name = "next") val next: String? = null,
    @Json(name = "previous") val previous: String? = null,
    @Json(name = "results") val results: List<Pokemon> = emptyList()
) {
    @JsonClass(generateAdapter = true)
    data class Pokemon(
        @Json(name = "name") val name: String? = null,
        @Json(name = "url") val url: String? = null
    )
}
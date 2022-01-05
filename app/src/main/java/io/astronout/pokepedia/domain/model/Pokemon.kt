package io.astronout.pokepedia.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.astronout.pokepedia.data.source.remote.response.PokemonDetailResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val image: String,
    val abilities: List<Ability> = emptyList(),
    val baseExperience: Int = 0,
    val height: Int = 0,
    val weight: Int = 0,
    val stats: List<Stat> = emptyList(),
    val types: List<Type> = emptyList()
): Parcelable {

    fun getIdString() = String.format("#%03d", id)

    @Parcelize
    data class Ability(
        val name: String,
        val isHidden: Boolean,
        val slot: Int
    ): Parcelable

    @Parcelize
    data class Stat(
        val baseStat: Int,
        val effort: Int,
        val name: String
    ): Parcelable

    @Parcelize
    data class Type(
        val slot: Int,
        val name: String
    ): Parcelable
}

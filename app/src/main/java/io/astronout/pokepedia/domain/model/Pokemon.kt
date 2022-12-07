package io.astronout.pokepedia.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val image: String,
    val abilities: List<Abilities>,
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val stats: List<Stats>,
    val types: List<Types>
): Parcelable {

    fun getIdString() = String.format("#%03d", id)

    @Parcelize
    data class Abilities(
        val name: String,
        val isHidden: Boolean,
        val slot: Int
    ): Parcelable

    @Parcelize
    data class Stats(
        val baseStat: Int,
        val effort: Int,
        val name: String,
        val color: Int
    ): Parcelable

    @Parcelize
    data class Types(
        val slot: Int,
        val name: String
    ): Parcelable
}

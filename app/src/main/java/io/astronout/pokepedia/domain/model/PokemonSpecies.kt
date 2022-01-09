package io.astronout.pokepedia.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonSpecies(
    val baseHappiness: Int,
    val captureRate: Int,
    val eggGroups: List<String> = emptyList(),
    val flavorTextEntries: List<FlavorTextEntry> = emptyList(),
    val genderRate: Int,
    val genera: List<Genera> = emptyList(),
    val growthRate: String,
    val habitat: String,
    val hatchCounter: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val palParkEncounters: List<PalParkEncounter> = emptyList(),
    val shape: String,
): Parcelable {

    @Parcelize
    data class FlavorTextEntry(
        val flavorText: String,
        val language: String,
        val version: String
    ): Parcelable

    @Parcelize
    data class Genera(
       val genus: String,
       val language: String
    ): Parcelable

    @Parcelize
    data class PalParkEncounter(
        val area: String,
        val baseScore: Int,
        val rate: Int,
    ): Parcelable

}
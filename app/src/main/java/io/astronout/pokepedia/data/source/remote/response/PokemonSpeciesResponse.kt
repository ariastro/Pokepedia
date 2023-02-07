package io.astronout.pokepedia.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.astronout.pokepedia.domain.model.PokemonSpecies
import io.astronout.pokepedia.utils.getEvolutionId

@JsonClass(generateAdapter = true)
data class PokemonSpeciesResponse(
    @field:Json(name = "base_happiness") val baseHappiness: Int,
    @field:Json(name = "capture_rate") val captureRate: Int,
    @field:Json(name = "egg_groups") val eggGroups: List<EggGroup>,
    @field:Json(name = "evolution_chain") val evolutionChain: EvolutionChain,
    @field:Json(name = "flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>,
    @field:Json(name = "gender_rate") val genderRate: Int,
    @field:Json(name = "genera") val genera: List<Genera>,
    @field:Json(name = "growth_rate") val growthRate: GrowthRate,
    @field:Json(name = "habitat") val habitat: Habitat,
    @field:Json(name = "hatch_counter") val hatchCounter: Int,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "order") val order: Int,
    @field:Json(name = "pal_park_encounters") val palParkEncounters: List<PalParkEncounter>,
    @field:Json(name = "shape") val shape: Shape,
) {

    @JsonClass(generateAdapter = true)
    data class Language(
        @field:Json(name = "name") val name: String,
        @field:Json(name = "url") val url: String
    )

    @JsonClass(generateAdapter = true)
    data class EggGroup(
        @field:Json(name = "name") val name: String,
        @field:Json(name = "url") val url: String
    )

    @JsonClass(generateAdapter = true)
    data class EvolutionChain(
        @field:Json(name = "url") val url: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class FlavorTextEntry(
        @field:Json(name = "flavor_text") val flavorText: String,
        @field:Json(name = "language") val language: Language,
        @field:Json(name = "version") val version: Version
    ) {
        @JsonClass(generateAdapter = true)
        data class Version(
            @field:Json(name = "name") val name: String,
            @field:Json(name = "url") val url: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Genera(
        @field:Json(name = "genus") val genus: String,
        @field:Json(name = "language") val language: Language
    )

    @JsonClass(generateAdapter = true)
    data class GrowthRate(
        @field:Json(name = "name") val name: String,
        @field:Json(name = "url") val url: String
    )

    @JsonClass(generateAdapter = true)
    data class Habitat(
        @field:Json(name = "name") val name: String,
        @field:Json(name = "url") val url: String
    )

    @JsonClass(generateAdapter = true)
    data class PalParkEncounter(
        @field:Json(name = "area") val area: Area,
        @field:Json(name = "base_score") val baseScore: Int,
        @field:Json(name = "rate") val rate: Int
    ) {
        @JsonClass(generateAdapter = true)
        data class Area(
            @field:Json(name = "name") val name: String,
            @field:Json(name = "url") val url: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Shape(
        @field:Json(name = "name") val name: String,
        @field:Json(name = "url") val url: String
    )

    fun toPokemonSpecies(): PokemonSpecies {
        return PokemonSpecies(
            baseHappiness = baseHappiness,
            captureRate = captureRate,
            eggGroups = eggGroups.map { it.name },
            evolutionChainId = evolutionChain.url?.getEvolutionId() ?: 0,
            flavorTextEntries = flavorTextEntries.map {
                PokemonSpecies.FlavorTextEntry(
                    flavorText = it.flavorText,
                    language = it.language.name,
                    version = it.version.name
                )
            },
            genderRate = genderRate,
            genera = genera.map {
                PokemonSpecies.Genera(
                    genus = it.genus,
                    language = it.language.name
                )
            },
            growthRate = growthRate.name,
            habitat = habitat.name,
            hatchCounter = hatchCounter,
            id = id,
            name = name,
            order = order,
            palParkEncounters = palParkEncounters.map {
                PokemonSpecies.PalParkEncounter(
                    area = it.area.name,
                    baseScore = it.baseScore,
                    rate = it.rate
                )
            },
            shape = shape.name
        )
    }
}
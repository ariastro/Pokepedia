package io.astronout.pokepedia.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.astronout.pokepedia.domain.model.PokemonEvolution

@JsonClass(generateAdapter = true)
data class PokemonEvolutionResponse(
    @Json(name = "baby_trigger_item") val babyTriggerItem: Any? = null,
    @Json(name = "chain") val chain: Chain? = null,
    @Json(name = "id") val id: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class Chain(
        @Json(name = "evolution_details") val evolutionDetails: List<EvolutionDetail>? = null,
        @Json(name = "evolves_to") val evolvesTo: List<EvolvesTo>? = null,
        @Json(name = "is_baby") val isBaby: Boolean? = null,
        @Json(name = "species") val species: Species? = null
    )

    @JsonClass(generateAdapter = true)
    data class EvolvesTo(
        @Json(name = "evolution_details") val evolutionDetails: List<EvolutionDetail>? = null,
        @Json(name = "evolves_to") val evolvesTo: List<EvolvesTo>? = null,
        @Json(name = "is_baby") val isBaby: Boolean? = null,
        @Json(name = "species") val species: Species? = null
    ) {
        fun toEvolvesTo(): PokemonEvolution.EvolvesTo {
            return PokemonEvolution.EvolvesTo(
                evolutionDetails = evolutionDetails?.map { it.toEvolutionDetail() }.orEmpty(),
                evolvesTo = evolvesTo?.map { it.toEvolvesTo() }.orEmpty(),
                isBaby = isBaby ?: false,
                species = PokemonEvolution.Species(species)
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class EvolutionDetail(
        @Json(name = "gender") val gender: Any? = null,
        @Json(name = "held_item") val heldItem: Any? = null,
        @Json(name = "item") val item: Any? = null,
        @Json(name = "known_move") val knownMove: Any? = null,
        @Json(name = "known_move_type") val knownMoveType: Any? = null,
        @Json(name = "location") val location: Any? = null,
        @Json(name = "min_affection") val minAffection: Any? = null,
        @Json(name = "min_beauty") val minBeauty: Any? = null,
        @Json(name = "min_happiness") val minHappiness: Any? = null,
        @Json(name = "min_level") val minLevel: Int? = null,
        @Json(name = "needs_overworld_rain") val needsOverworldRain: Boolean? = null,
        @Json(name = "party_species") val partySpecies: Any? = null,
        @Json(name = "party_type") val partyType: Any? = null,
        @Json(name = "relative_physical_stats") val relativePhysicalStats: Any? = null,
        @Json(name = "time_of_day") val timeOfDay: String? = null,
        @Json(name = "trade_species") val tradeSpecies: Any? = null,
        @Json(name = "trigger") val trigger: Trigger? = null,
        @Json(name = "turn_upside_down") val turnUpsideDown: Boolean? = null
    ) {
        fun toEvolutionDetail(): PokemonEvolution.EvolutionDetail {
            return PokemonEvolution.EvolutionDetail(
                gender = gender ?: Any(),
                heldItem = heldItem ?: Any(),
                item = item ?: Any(),
                knownMove = knownMove ?: Any(),
                knownMoveType = knownMoveType ?: Any(),
                location = location ?: Any(),
                minAffection = minAffection ?: Any(),
                minBeauty = minBeauty ?: Any(),
                minHappiness = minHappiness ?: Any(),
                minLevel = minLevel ?: 0,
                needsOverworldRain = needsOverworldRain ?: false,
                partySpecies = partySpecies ?: Any(),
                partyType = partyType ?: Any(),
                relativePhysicalStats = relativePhysicalStats ?: Any(),
                timeOfDay = timeOfDay.orEmpty(),
                tradeSpecies = tradeSpecies ?: Any(),
                trigger = PokemonEvolution.Trigger(trigger),
                turnUpsideDown = turnUpsideDown ?: false
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class Trigger(
        @Json(name = "name") val name: String? = null,
        @Json(name = "url") val url: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Species(
        @Json(name = "name") val name: String? = null,
        @Json(name = "url") val url: String? = null
    )

    fun toPokemonEvolution(): PokemonEvolution {
        return PokemonEvolution(
            babyTriggerItem = babyTriggerItem ?: Any(),
            chain = PokemonEvolution.Chain(
                evolutionDetails = chain?.evolutionDetails?.map { it.toEvolutionDetail() }.orEmpty(),
                evolvesTo = chain?.evolvesTo?.map { it.toEvolvesTo() }.orEmpty(),
                isBaby = chain?.isBaby ?: false,
                species = PokemonEvolution.Species(chain?.species)
            ),
            id = id ?: 0
        )
    }

}
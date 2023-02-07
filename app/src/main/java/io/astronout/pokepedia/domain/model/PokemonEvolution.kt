package io.astronout.pokepedia.domain.model

import io.astronout.pokepedia.data.source.remote.response.PokemonEvolutionResponse

data class PokemonEvolution(
    val babyTriggerItem: Any,
    val chain: Chain,
    val id: Int
) {
    data class Chain(
        val evolutionDetails: List<EvolutionDetail>,
        val evolvesTo: List<EvolvesTo>,
        val isBaby: Boolean,
        val species: Species
    )

    data class EvolvesTo(
        val evolutionDetails: List<EvolutionDetail>,
        val evolvesTo: List<EvolvesTo>,
        val isBaby: Boolean,
        val species: Species,
    )

    data class EvolutionDetail(
        val gender: Any,
        val heldItem: Any,
        val item: Any,
        val knownMove: Any,
        val knownMoveType: Any,
        val location: Any,
        val minAffection: Any,
        val minBeauty: Any,
        val minHappiness: Any,
        val minLevel: Int,
        val needsOverworldRain: Boolean,
        val partySpecies: Any,
        val partyType: Any,
        val relativePhysicalStats: Any,
        val timeOfDay: String,
        val tradeSpecies: Any,
        val trigger: Trigger,
        val turnUpsideDown: Boolean
    )

    data class Trigger(
        val name: String,
        val url: String
    ) {
        constructor(data: PokemonEvolutionResponse.Trigger?) : this(
            name = data?.name.orEmpty(),
            url = data?.url.orEmpty()
        )
    }

    data class Species(
        val name: String,
        val url: String
    ) {
        constructor(data: PokemonEvolutionResponse.Species?) : this(
            name = data?.name.orEmpty(),
            url = data?.url.orEmpty()
        )
    }

}
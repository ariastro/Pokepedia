package io.astronout.pokepedia.data.source.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.utils.getPokemonImage

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    @field:Json(name = "abilities") val abilities: List<Abilities> = emptyList(),
    @field:Json(name = "base_experience") val baseExperience: Int,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "stats") val stats: List<Stats> = emptyList(),
    @field:Json(name = "types") val types: List<Types> = emptyList(),
    @field:Json(name = "weight") val weight: Int
) {

    @JsonClass(generateAdapter = true)
    data class Abilities(
        @field:Json(name = "ability") val ability: Ability,
        @field:Json(name = "is_hidden") val isHidden: Boolean,
        @field:Json(name = "slot") val slot: Int
    ) {
        @JsonClass(generateAdapter = true)
        data class Ability(
            @field:Json(name = "name") val name: String,
            @field:Json(name = "url") val url: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Stats(
        @field:Json(name = "base_stat") val baseStat: Int,
        @field:Json(name = "effort") val effort: Int,
        @field:Json(name = "stat") val stat: Stat
    ) {
        @JsonClass(generateAdapter = true)
        data class Stat(
            @field:Json(name = "name") val name: String,
            @field:Json(name = "url") val url: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Types(
        @field:Json(name = "slot") val slot: Int,
        @field:Json(name = "type") val type: Type
    ) {
        @JsonClass(generateAdapter = true)
        data class Type(
            @field:Json(name = "name") val name: String,
            @field:Json(name = "url") val url: String
        )
    }

    fun toPokemon(): Pokemon {
        return Pokemon(
            id = id,
            name = name,
            image = id.getPokemonImage(),
            abilities = abilities.map {
                Pokemon.Abilities(
                    name = it.ability.name,
                    isHidden = it.isHidden,
                    slot = it.slot
                )
            },
            baseExperience = baseExperience,
            height = height,
            weight = weight,
            stats = stats.map {
                Pokemon.Stats(
                    baseStat = it.baseStat,
                    effort = it.effort,
                    name = it.stat.name
                )
            },
            types = types.map {
                Pokemon.Types(
                    slot = it.slot,
                    name = it.type.name
                )
            }
        )
    }

}
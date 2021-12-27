package io.astronout.pokepedia.domain.repository

import androidx.paging.PagingData
import io.astronout.pokepedia.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokepediaRepository {

    fun getAllPokemons(): Flow<PagingData<Pokemon>>

}
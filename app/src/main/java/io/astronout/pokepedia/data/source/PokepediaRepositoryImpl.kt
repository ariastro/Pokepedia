package io.astronout.pokepedia.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.astronout.pokepedia.data.source.remote.RemoteDataSource
import io.astronout.pokepedia.data.source.remote.paging.PokepediaPagingSource
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.domain.repository.PokepediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokepediaRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PokepediaRepository {

    override fun getAllPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                PokepediaPagingSource(remoteDataSource)
            }
        ).flow
    }

    companion object {
        const val STARTING_OFFSET_INDEX = 0
        const val NETWORK_PAGE_SIZE = 20
    }

}


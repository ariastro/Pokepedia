package io.astronout.pokepedia.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import io.astronout.pokepedia.data.source.remote.RemoteDataSource
import io.astronout.pokepedia.data.source.remote.paging.PokepediaPagingSource
import io.astronout.pokepedia.domain.model.Pokemon
import io.astronout.pokepedia.domain.repository.PokepediaRepository
import io.astronout.pokepedia.vo.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class PokepediaRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : PokepediaRepository {

    override fun getAllPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                PokepediaPagingSource(remoteDataSource)
            }
        ).flow
    }

    override fun getPokemonDetails(id: Int) = flow {
        remoteDataSource.getPokemonDetails(id).let {
            it.suspendOnSuccess {
                emit(Resource.Success(data.toPokemon()))
            }.suspendOnError {
                emit(Resource.Error(message()))
            }.suspendOnException {
                emit(Resource.Error(message.orEmpty()))
            }
        }
    }.onStart { emit(Resource.Loading) }.flowOn(ioDispatcher)

    override fun getPokemonSpecies(id: Int) = flow {
        remoteDataSource.getPokemonSpecies(id).let {
            it.suspendOnSuccess {
                emit(Resource.Success(data.toPokemonSpecies()))
            }.suspendOnError {
                emit(Resource.Error(message()))
            }.suspendOnException {
                emit(Resource.Error(message.orEmpty()))
            }
        }
    }.onStart { emit(Resource.Loading) }.flowOn(ioDispatcher)

    companion object {
        const val STARTING_OFFSET_INDEX = 0
        const val NETWORK_PAGE_SIZE = 20
    }

}


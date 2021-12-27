package io.astronout.pokepedia.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skydoves.sandwich.suspendOnSuccess
import io.astronout.pokepedia.data.source.PokepediaRepositoryImpl.Companion.STARTING_OFFSET_INDEX
import io.astronout.pokepedia.data.source.remote.RemoteDataSource
import io.astronout.pokepedia.data.source.remote.response.PokemonResponse
import io.astronout.pokepedia.domain.model.Pokemon
import retrofit2.HttpException
import java.io.IOException

class PokepediaPagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Pokemon>() {

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val offset = params.key ?: STARTING_OFFSET_INDEX

        return try {
            var response: PokemonResponse? = null
            remoteDataSource.getAllPokemons(
                limit = params.loadSize,
                offset = offset
            ).let {
                it.suspendOnSuccess {
                    response = data
                }
            }
            LoadResult.Page(
                data = response?.results?.map { it.toPokemon() } ?: emptyList(),
                prevKey = if (offset == STARTING_OFFSET_INDEX) null else offset - params.loadSize,
                nextKey = if (response?.next == null) null else offset + params.loadSize
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
package io.astronout.pokepedia.data.source

import io.astronout.pokepedia.data.source.remote.RemoteDataSource
import io.astronout.pokepedia.domain.repository.PokepediaRepository
import javax.inject.Inject

class PokepediaRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PokepediaRepository {

    companion object {
        const val STARTING_OFFSET_INDEX = 0
    }

}


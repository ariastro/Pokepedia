package io.astronout.pokepedia.data.source

import io.astronout.pokepedia.data.source.remote.RemoteDataSource
import io.astronout.pokepedia.domain.repository.PokepediaRepository
import javax.inject.Inject

class PokepediaRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
    ) : PokepediaRepository {

}
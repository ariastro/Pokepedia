package io.astronout.pokepedia.di

import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.astronout.pokepedia.BuildConfig
import io.astronout.pokepedia.data.source.PokepediaRepositoryImpl
import io.astronout.pokepedia.data.source.remote.PokepediaService
import io.astronout.pokepedia.data.source.remote.RemoteDataSource
import io.astronout.pokepedia.domain.repository.PokepediaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePokepediaService(retrofit: Retrofit): PokepediaService =
        retrofit.create(PokepediaService::class.java)

    @Provides
    @Singleton
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(pokepediaService: PokepediaService) = RemoteDataSource(pokepediaService)

    @Provides
    @Singleton
    fun providePokepediaRepository(remoteDataSource: RemoteDataSource, ioDispatcher: CoroutineDispatcher): PokepediaRepository = PokepediaRepositoryImpl(remoteDataSource, ioDispatcher)

}
package com.example.googlebookfinder.data.di

import com.example.googlebookfinder.data.network.BookFinderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
    }

    @Provides
    @Singleton
    fun providePoiDataService(
        retrofitBuilder: Retrofit.Builder,
    ): BookFinderService {
        val retrofit = retrofitBuilder
            .baseUrl("https://www.googleapis.com/books/")
            .build()

        return retrofit.create(BookFinderService::class.java)
    }
}

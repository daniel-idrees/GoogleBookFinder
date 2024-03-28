package com.example.data.di

import com.example.data.network.BookFinderService
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
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder().build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)

    @Provides
    @Singleton
    fun provideBookFinderService(
        retrofitBuilder: Retrofit.Builder,
    ): BookFinderService =
        retrofitBuilder
            .baseUrl("https://www.googleapis.com/books/")
            .build()
            .create(BookFinderService::class.java)
}

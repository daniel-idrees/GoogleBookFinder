package com.example.data.network.di

import com.example.data.network.BookNetworkDataSource
import com.example.data.network.retrofit.RetrofitBookNetwork
import com.example.data.network.retrofit.RetrofitBookNetworkApi
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
    fun provideRetrofitBookNetworkApi(
        retrofitBuilder: Retrofit.Builder,
    ): RetrofitBookNetworkApi =
        retrofitBuilder
            .baseUrl("https://www.googleapis.com/books/")
            .build()
            .create(RetrofitBookNetworkApi::class.java)


    @Provides
    @Singleton
    fun provideBookNetworkDataSource(
        network: RetrofitBookNetwork
    ): BookNetworkDataSource = network
}

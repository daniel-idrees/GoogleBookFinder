package com.example.data.repository.di

import com.example.data.repository.BookNetworkRepository
import com.example.data.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    @Singleton
    fun providesBookRepository(
        impl: BookNetworkRepository,
    ): BookRepository
}

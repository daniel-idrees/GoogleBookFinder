package com.example.data.di

import com.example.data.repository.BookRepositoryImpl
import com.example.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    @Singleton
    fun providesBookRepository(
        impl: BookRepositoryImpl,
    ): BookRepository
}

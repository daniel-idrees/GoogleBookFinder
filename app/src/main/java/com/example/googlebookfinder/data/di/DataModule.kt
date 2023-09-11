package com.example.googlebookfinder.data.di

import com.example.googlebookfinder.data.network.BookFinderService
import com.example.googlebookfinder.data.repository.BookRepositoryImpl
import com.example.googlebookfinder.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun providesBookRepository(
        bookFinderService: BookFinderService,
    ): BookRepository = BookRepositoryImpl(bookFinderService)
}

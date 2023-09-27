package com.example.googlebookfinder.di

import com.example.data.repository.BookRepositoryImpl
import com.example.domain.repository.BookRepository
import com.example.domain.usecase.GetBookListUseCase
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
        impl: BookRepositoryImpl,
    ): BookRepository = impl

    @Provides
    fun providesUseCase(
        bookRepository: BookRepository,
    ): GetBookListUseCase = GetBookListUseCase(bookRepository)
}

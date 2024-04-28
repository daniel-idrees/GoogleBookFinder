package com.example.domain

import com.example.data.repository.BookRepository
import com.example.domain.usecase.GetBookListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun providesUseCase(
        bookRepository: BookRepository,
    ): GetBookListUseCase = GetBookListUseCase(bookRepository)
}

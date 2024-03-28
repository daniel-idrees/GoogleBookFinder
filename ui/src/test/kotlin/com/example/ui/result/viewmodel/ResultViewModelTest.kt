package com.example.ui.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.common.MainDispatcherRule
import com.example.domain.model.Book
import com.example.domain.model.BookDataResult
import com.example.domain.usecase.GetBookListUseCase
import com.example.ui.nav.ResultScreenArgumentSearchQueryKey
import com.example.ui.result.state.BookSearchResultState
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class ResultViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getBookListUseCase: GetBookListUseCase = mock()

    private val savedStateHandle: SavedStateHandle = mock {
        whenever(mock.get<String>(ResultScreenArgumentSearchQueryKey)) doReturn "query"
    }

    @Test
    fun `view state should be success if the usecase returns list of books`() = runBlocking {
        val bookList = listOf(Book("", emptyList(), ""))
        whenever(getBookListUseCase.get("query")) doReturn BookDataResult.Success(bookList)
        val subject = ResultViewModel(
            getBookListUseCase,
            savedStateHandle,
        )
        subject.bookSearchResultState.value shouldBe BookSearchResultState.Success(bookList)
    }


    @Test
    fun `view state should be empty if the usecase returns empty list`() {
        runBlocking {
            whenever(getBookListUseCase.get("query")) doReturn BookDataResult.Empty
            val subject = ResultViewModel(
                getBookListUseCase,
                savedStateHandle,
            )
            subject.bookSearchResultState.value shouldBe BookSearchResultState.EmptyResult
        }
    }

    @Test
    fun `view state should be error if the usecase returns null`() {
        runBlocking {
            whenever(getBookListUseCase.get("query")) doReturn BookDataResult.Error("error")
            val subject = ResultViewModel(
                getBookListUseCase,
                savedStateHandle,
            )
            subject.bookSearchResultState.value shouldBe BookSearchResultState.Error("error")
        }
    }

    @Test
    fun `view state should be error there is no search query argument`() {
        whenever(savedStateHandle.get<String>(ResultScreenArgumentSearchQueryKey)) doReturn null
        val subject = ResultViewModel(
            getBookListUseCase,
            savedStateHandle,
        )
        subject.bookSearchResultState.value shouldBe BookSearchResultState.Error("Something went wrong")
    }
}

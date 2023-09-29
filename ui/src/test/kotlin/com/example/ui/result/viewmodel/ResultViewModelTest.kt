package com.example.ui.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.common.MainDispatcherRule
import com.example.domain.model.Book
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

    private val mockQuery = "mockQuery"

    private val savedStateHandle: SavedStateHandle = mock {
        whenever(this.mock.get<String>(ResultScreenArgumentSearchQueryKey)) doReturn mockQuery
    }

    @Test
    fun `view state should be success if the usecase returns list of books`() {
        val mockBookList = listOf(Book())
        runBlocking {
            whenever(getBookListUseCase.get(mockQuery)) doReturn mockBookList
            val subject = ResultViewModel(
                getBookListUseCase,
                savedStateHandle,
                mainDispatcherRule.testDispatcher,
            )
            subject.bookSearchResultState.value shouldBe BookSearchResultState.Success(mockBookList)
        }
    }

    @Test
    fun `view state should be empty if the usecase returns empty list`() {
        val mockBookList: List<Book> = emptyList()
        runBlocking {
            whenever(getBookListUseCase.get(mockQuery)) doReturn mockBookList
            val subject = ResultViewModel(
                getBookListUseCase,
                savedStateHandle,
                mainDispatcherRule.testDispatcher,
            )
            subject.bookSearchResultState.value shouldBe BookSearchResultState.EmptyResult
        }
    }

    @Test
    fun `view state should be error if the usecase returns null`() {
        runBlocking {
            whenever(getBookListUseCase.get(mockQuery)) doReturn null
            val subject = ResultViewModel(
                getBookListUseCase,
                savedStateHandle,
                mainDispatcherRule.testDispatcher,
            )
            subject.bookSearchResultState.value shouldBe BookSearchResultState.Error
        }
    }

    @Test
    fun `view state should be error there is no search query argument`() {
        whenever(savedStateHandle.get<String>(ResultScreenArgumentSearchQueryKey)) doReturn null
        val subject = ResultViewModel(
            getBookListUseCase,
            savedStateHandle,
            mainDispatcherRule.testDispatcher,
        )
        subject.bookSearchResultState.value shouldBe BookSearchResultState.Error
    }
}

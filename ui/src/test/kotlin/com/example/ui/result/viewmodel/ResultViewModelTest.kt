package com.example.ui.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.common.MainDispatcherRule
import com.example.data.model.Book
import com.example.data.model.dto.BookDataResult
import com.example.domain.usecase.GetBookListUseCase
import com.example.ui.result.action.ResultAction
import com.example.ui.result.nav.SEARCH_QUERY_ARG
import com.example.ui.result.state.ResultViewState
import com.example.ui.result.uievent.ResultUiEvent
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class ResultViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val getBookListUseCase: GetBookListUseCase = mock()

    private val savedStateHandle: SavedStateHandle = mock {
        whenever(mock.get<String>(SEARCH_QUERY_ARG)) doReturn "query"

        whenever(
            mock.getStateFlow(
                key = SEARCH_QUERY_ARG,
                initialValue = ""
            )
        ) doReturn MutableStateFlow("query").asStateFlow()
    }

    private val subject by lazy {
        ResultViewModel(
            getBookListUseCase,
            savedStateHandle,
            mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun `view state should be loading initially`() = runTest {
        subject.resultViewState.value shouldBe ResultViewState.Loading
    }

    @Test
    fun `view state should be success if the usecase returns list of books`() = runTest {
        // when
        val bookList = listOf(com.example.data.model.Book("", emptyList(), ""))
        whenever(getBookListUseCase("query")) doReturn flowOf(com.example.data.model.dto.BookDataResult.Success(bookList))

        // then
        subject.resultViewState.test {
            val result = awaitItem()
            result shouldBe ResultViewState.Success(bookList)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `view state should be empty if the usecase returns empty list`() = runTest {
        // when
        whenever(getBookListUseCase("query")) doReturn flowOf(com.example.data.model.dto.BookDataResult.Empty)

        // then
        subject.resultViewState.test {
            val result = awaitItem()
            result shouldBe ResultViewState.Empty
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `view state should be error if the usecase returns error`() = runTest {
        // when
        whenever(getBookListUseCase("query")) doReturn flowOf(com.example.data.model.dto.BookDataResult.Error)

        // then
        subject.resultViewState.test {
            val result = awaitItem()
            result shouldBe ResultViewState.Error
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `view state should be no internet connection if the usecase returns  no internet connection`() =
        runTest {
            // when
            whenever(getBookListUseCase("query")) doReturn flowOf(com.example.data.model.dto.BookDataResult.NoInternetConnection)

            // then
            subject.resultViewState.test {
                val result = awaitItem()
                result shouldBe ResultViewState.NoInternetConnection
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `ResultItemClickAction should send the ui event to navigate to details`() = runTest {
        subject.events.test {
            subject.onAction(ResultAction.ResultItemClicked)
            awaitItem() shouldBe ResultUiEvent.NavigateToDetails
        }
    }

    @Test
    fun `NoInternetConnectionAction should send the ui event to show internet error`() = runTest {
        subject.events.test {
            subject.onAction(ResultAction.NoInternetConnectionAction)
            awaitItem() shouldBe ResultUiEvent.ShowNoInternetConnectionError
        }
    }

    @Test
    fun `ErrorAction should send the ui event to show error`() = runTest {
        subject.events.test {
            subject.onAction(ResultAction.ErrorAction)
            awaitItem() shouldBe ResultUiEvent.ShowError
        }
    }

    @Test
    fun `GoBackButtonClicked should send the ui event to navigate back`() = runTest {
        subject.events.test {
            subject.onAction(ResultAction.GoBackButtonClicked)
            awaitItem() shouldBe ResultUiEvent.NavigateBack
        }
    }

    @Test
    fun `AfterErrorShownAction should send the ui event to navigate back`() = runTest {
        subject.events.test {
            subject.onAction(ResultAction.AfterErrorShownAction)
            awaitItem() shouldBe ResultUiEvent.NavigateBack
        }
    }
}

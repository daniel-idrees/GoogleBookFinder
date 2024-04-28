package com.example.ui.search.viewmodel

import app.cash.turbine.test
import com.example.common.MainDispatcherRule
import com.example.ui.search.action.SearchAction
import com.example.ui.search.uievent.SearchUiEvent
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

internal class SearchViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val subject by lazy {
        SearchViewModel(
            mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun `SearchButtonClicked should send the ui event show error if query is black or empty`() =
        runTest {
            subject.events.test {
                subject.onAction(SearchAction.SearchButtonClicked(""))
                awaitItem() shouldBe SearchUiEvent.EmptyTextError

                subject.onAction(SearchAction.SearchButtonClicked("  "))
                awaitItem() shouldBe SearchUiEvent.EmptyTextError
            }
        }

    @Test
    fun `SearchButtonClicked should send the ui event to navigate to result`() = runTest {
        subject.events.test {
            subject.onAction(SearchAction.SearchButtonClicked("query"))
            awaitItem() shouldBe SearchUiEvent.NavigateToResult("query")
        }
    }
}
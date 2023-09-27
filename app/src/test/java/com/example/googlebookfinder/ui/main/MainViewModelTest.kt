package com.example.googlebookfinder.ui.main

import com.example.googlebookfinder.domain.model.Book
import com.example.googlebookfinder.domain.usecase.GetBookListUseCase
import com.example.googlebookfinder.ui.main.state.BookSearchResultState
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val getBookListUseCase: GetBookListUseCase = mock()

    private val subject = MainViewModel(getBookListUseCase, testDispatcher)

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get should return null if the repository returns null`() {
        val mockBookList = listOf(Book())
        runBlocking {
            whenever(getBookListUseCase.get("mockQuery")) doReturn mockBookList
            subject.searchBook("mockQuery")
            subject.bookSearchResultState.value shouldBe BookSearchResultState.Success(mockBookList)
        }
    }

    @Test
    fun `get should return null if the repository returns`() {
        runBlocking {
            whenever(getBookListUseCase.get("mockQuery")) doReturn null
            subject.searchBook("mockQuery")
            subject.bookSearchResultState.value shouldBe BookSearchResultState.Error
        }
    }
}

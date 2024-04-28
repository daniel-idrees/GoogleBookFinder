package com.example.data.repository

import app.cash.turbine.test
import com.example.data.common.MainDispatcherRule
import com.example.data.model.Book
import com.example.data.model.BookDataResult
import com.example.data.network.BookNetworkDataSource
import com.example.data.utils.FakeObjects.authorList
import com.example.data.utils.FakeObjects.bookTitle
import com.example.data.utils.FakeObjects.emptySearchResult
import com.example.data.utils.FakeObjects.fakeResponse
import com.example.data.utils.FakeObjects.queryString
import com.example.data.utils.FakeObjects.thumbnailUrlWithHttps
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class BookNetworkRepositoryTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val network: BookNetworkDataSource = mock()

    private val subject by lazy { BookNetworkRepository(network) }

    @Test
    fun `getBooks should return success when api response is successful and list is not empty`() =
        runTest {
            // when
            whenever(network.searchBooks(queryString)) doReturn fakeResponse

            // then
            val result = subject.searchBooks(queryString)
            verify(network).searchBooks(queryString)
            verifyNoMoreInteractions(network)

            val expected = listOf(Book(bookTitle, authorList, thumbnailUrlWithHttps))

            result.test {
                awaitItem() shouldBe BookDataResult.Success(expected)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getBooks should return empty when api response is successful and list is empty`() =
        runTest {
            // when
            whenever(network.searchBooks(queryString)) doReturn emptySearchResult

            // then
            val result = subject.searchBooks(queryString)
            verify(network).searchBooks(queryString)
            verifyNoMoreInteractions(network)

            result.test {
                awaitItem() shouldBe BookDataResult.Empty
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `getBooks should return general error when api response throws general exception`() =
        runTest {
            // when
            whenever(network.searchBooks(queryString)) doThrow IllegalArgumentException()

            // then
            val result = subject.searchBooks(queryString)
            verify(network).searchBooks(queryString)
            verifyNoMoreInteractions(network)

            result.test {
                awaitItem() shouldBe BookDataResult.Error
                cancelAndIgnoreRemainingEvents()
            }
        }
}

package com.example.data.repository

import com.example.data.common.MainDispatcherRule
import com.example.data.network.BookFinderService
import com.example.data.utils.FakeObjects.authorList
import com.example.data.utils.FakeObjects.bookTitle
import com.example.data.utils.FakeObjects.emptySearchBookResponse
import com.example.data.utils.FakeObjects.fakeResponse
import com.example.data.utils.FakeObjects.queryString
import com.example.data.utils.FakeObjects.thumbnailUrlWithHttps
import com.example.domain.model.Book
import com.example.domain.model.BookDataResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class BookRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val bookFinderService: BookFinderService = mock()

    private val subject = BookRepositoryImpl(bookFinderService)

    @Test
    fun `getBooks should return success when api response is successful and list is not empty`() =
        runBlocking {
            val expected = listOf(Book(bookTitle, authorList, thumbnailUrlWithHttps))

            whenever(bookFinderService.getBookList(queryString)) doReturn fakeResponse
            val result = subject.getBooks(queryString)
            verify(bookFinderService).getBookList(queryString)
            verifyNoMoreInteractions(bookFinderService)

            result shouldBe BookDataResult.Success(expected)
        }

    @Test
    fun `getBooks should return empty when api response is successful and list is empty`() =
        runBlocking {
            whenever(bookFinderService.getBookList(queryString)) doReturn emptySearchBookResponse
            val result = subject.getBooks(queryString)
            verify(bookFinderService).getBookList(queryString)
            verifyNoMoreInteractions(bookFinderService)
            result shouldBe BookDataResult.Empty
        }


    @Test
    fun `getBooks should return general error when api response throws general exception`() =
        runBlocking {
            whenever(bookFinderService.getBookList(queryString)) doThrow IllegalArgumentException()
            val result = subject.getBooks(queryString)
            verify(bookFinderService).getBookList(queryString)
            verifyNoMoreInteractions(bookFinderService)
            result shouldBe BookDataResult.Error
        }
}


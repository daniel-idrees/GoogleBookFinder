package com.example.data.repository

import com.example.data.common.MainDispatcherRule
import com.example.data.dto.ImageLinks
import com.example.data.dto.Item
import com.example.data.dto.SearchBookResponse
import com.example.data.dto.VolumeInfo
import com.example.data.network.BookFinderService
import com.example.domain.model.Book
import com.example.domain.model.BookDataResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class BookRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val bookFinderService: BookFinderService = mock()
    private val queryString = "fakeQuery"

    private val bookTitle = "fakeTitle"
    private val authorList = listOf("fakeAuthor")
    private val thumbnailUrlWithHttp = "http://fakeUrl"
    private val thumbnailUrlWithHttps = "https://fakeUrl"

    private val fakeResponse = SearchBookResponse(
        "",
        1,
        listOf(
            Item(
                volumeInfo = VolumeInfo(
                    title = bookTitle,
                    authors = authorList,
                    imageLinks = ImageLinks(thumbnail = thumbnailUrlWithHttp),
                ),
            ),
        ),
    )

    private val emptySearchBookResponse = SearchBookResponse(
        "",
        1,
        emptyList(),
    )

    private val expected = listOf(Book(bookTitle, authorList, thumbnailUrlWithHttps))

    private val subject = BookRepositoryImpl(bookFinderService, mainDispatcherRule.testDispatcher)

    @Test
    fun `getBooks should return success when api response is successful and list is not empty`() =
        runBlocking {
            whenever(bookFinderService.getBookList(queryString)) doReturn fakeResponse
            val result = subject.getBooks(queryString)
            verify(bookFinderService).getBookList(queryString)
            verifyNoMoreInteractions(bookFinderService)

            result shouldBe BookDataResult.Success(expected)
        }


    @Test
    fun `getBooks should return the thumbnail unchanged if it already has https`() =
        runBlocking {
            val searchResponse = fakeResponse.copy(
                items = listOf(
                    Item(
                        volumeInfo = VolumeInfo(
                            title = bookTitle,
                            authors = authorList,
                            imageLinks = ImageLinks(thumbnail = thumbnailUrlWithHttps),
                        ),
                    ),
                )
            )

            whenever(bookFinderService.getBookList(queryString)) doReturn searchResponse
            val result = subject.getBooks(queryString)
            verify(bookFinderService).getBookList(queryString)
            verifyNoMoreInteractions(bookFinderService)

            result shouldBe BookDataResult.Success(expected)
        }

    @Test
    fun `getBooks should return the thumbnail null if it was null in the response`() =
        runBlocking {
            val searchResponse = fakeResponse.copy(
                items = listOf(
                    Item(
                        volumeInfo = VolumeInfo(
                            title = bookTitle,
                            authors = authorList,
                            imageLinks = null,
                        ),
                    ),
                )
            )

            val expectedResultWithNoThumbnail = listOf(Book(bookTitle, authorList, null))

            whenever(bookFinderService.getBookList(queryString)) doReturn searchResponse
            val result = subject.getBooks(queryString)
            verify(bookFinderService).getBookList(queryString)
            verifyNoMoreInteractions(bookFinderService)

            result shouldBe BookDataResult.Success(expectedResultWithNoThumbnail)
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
            result shouldNotBe BookDataResult.Success(expected)
            result shouldBe BookDataResult.Error("Something went wrong")
        }
}


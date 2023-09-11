package com.example.googlebookfinder.data.repository

import com.example.googlebookfinder.data.dto.ImageLinks
import com.example.googlebookfinder.data.dto.Item
import com.example.googlebookfinder.data.dto.SearchBookResponse
import com.example.googlebookfinder.data.dto.VolumeInfo
import com.example.googlebookfinder.data.network.BookFinderService
import com.example.googlebookfinder.domain.model.Book
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class BookRepositoryImplTest {
    private val bookFinderService: BookFinderService = mock()
    private val mockQueryString = "mockQuery"
    private val subject = BookRepositoryImpl(bookFinderService)

    private val mockTitle = "mockTitle"
    private val mockAuthorList = listOf("mockAuthor")
    private val mockUrl = "mockUrl"

    private val mockResponse = SearchBookResponse(
        "",
        1,
        listOf(
            Item(
                volumeInfo = VolumeInfo(
                    title = mockTitle,
                    authors = mockAuthorList,
                    imageLinks = ImageLinks(thumbnail = mockUrl),
                ),
            ),
        ),
    )
    private val expected = listOf(Book(mockTitle, mockAuthorList, mockUrl))

    @Test
    fun `getBooks should return book list when api response is successful`() {
        runBlocking {
            whenever(bookFinderService.getBookList(mockQueryString)) doReturn mockResponse
            val result = subject.getBooks(mockQueryString)
            verify(bookFinderService).getBookList(mockQueryString)
            verifyNoMoreInteractions(bookFinderService)
            result shouldBe expected
        }
    }

    @Test
    fun `getBooks should return null when api response throws exception`() {
        runBlocking {
            whenever(bookFinderService.getBookList(mockQueryString)) doThrow IllegalArgumentException()
            val result = subject.getBooks(mockQueryString)
            verify(bookFinderService).getBookList(mockQueryString)
            verifyNoMoreInteractions(bookFinderService)
            result shouldNotBe expected
            result shouldBe null
        }
    }
}

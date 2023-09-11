package com.example.googlebookfinder.domain.usecase

import com.example.googlebookfinder.domain.model.Book
import com.example.googlebookfinder.domain.repository.BookRepository
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class GetBookListUseCaseTest {
    private val bookRepository: BookRepository = mock()
    private val subject = GetBookListUseCase(bookRepository)

    private val mockQuery = "mockQuery"

    @Test
    fun `get should return book list if the repository returns the book list`() {
        val mockBookList = listOf<Book>()
        runBlocking {
            whenever(bookRepository.getBooks(mockQuery)) doReturn mockBookList
            val result = subject.get(mockQuery)
            verify(bookRepository).getBooks(mockQuery)
            verifyNoMoreInteractions(bookRepository)
            result shouldBe mockBookList
        }
    }

    @Test
    fun `get should return null if the repository returns null`() {
        runBlocking {
            whenever(bookRepository.getBooks(mockQuery)) doReturn null
            val result = subject.get(mockQuery)
            verify(bookRepository).getBooks(mockQuery)
            verifyNoMoreInteractions(bookRepository)
            result shouldBe null
        }
    }
}

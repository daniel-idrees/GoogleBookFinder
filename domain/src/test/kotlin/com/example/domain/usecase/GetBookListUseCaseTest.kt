package com.example.domain.usecase

import com.example.domain.model.Book
import com.example.domain.model.BookDataResult
import com.example.domain.repository.BookRepository
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
            whenever(bookRepository.getBooks(mockQuery)) doReturn BookDataResult.Success(mockBookList)
            val result = subject.get(mockQuery)
            verify(bookRepository).getBooks(mockQuery)
            verifyNoMoreInteractions(bookRepository)
            result shouldBe BookDataResult.Success(mockBookList)
        }
    }

    @Test
    fun `get should return error if the repository returns error`() {
        runBlocking {
            whenever(bookRepository.getBooks(mockQuery)) doReturn BookDataResult.Error("error")
            val result = subject.get(mockQuery)
            verify(bookRepository).getBooks(mockQuery)
            verifyNoMoreInteractions(bookRepository)
            result shouldBe BookDataResult.Error("error")
        }
    }

    @Test
    fun `get should return empty if the repository returns empty`() {
        runBlocking {
            whenever(bookRepository.getBooks(mockQuery)) doReturn BookDataResult.Empty
            val result = subject.get(mockQuery)
            verify(bookRepository).getBooks(mockQuery)
            verifyNoMoreInteractions(bookRepository)
            result shouldBe BookDataResult.Empty
        }
    }
}

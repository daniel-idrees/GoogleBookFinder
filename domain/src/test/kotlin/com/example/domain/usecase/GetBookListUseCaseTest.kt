package com.example.domain.usecase

import app.cash.turbine.test
import com.example.data.model.Book
import com.example.data.model.BookDataResult
import com.example.data.repository.BookRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class GetBookListUseCaseTest {
    private val bookRepository: BookRepository = mock()
    private val subject by lazy {
        GetBookListUseCase(bookRepository)
    }

    private val fakeQuery = "fakeQuery"

    @Test
    fun `get should return book list if the repository returns the book list`() = runTest {
        // when
        val bookList = listOf(Book("", listOf(), ""))
        whenever(bookRepository.searchBooks(fakeQuery)) doReturn flowOf(
            BookDataResult.Success(
                bookList
            )
        )

        // then
        subject(fakeQuery).test {
            verify(bookRepository).searchBooks(fakeQuery)
            verifyNoMoreInteractions(bookRepository)
            val result = awaitItem()
            result shouldBe BookDataResult.Success(bookList)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `get should return error if the repository returns error`() = runTest {
        // when
        whenever(bookRepository.searchBooks(fakeQuery)) doReturn flowOf(BookDataResult.Error)

        // then
        subject(fakeQuery).test {
            verify(bookRepository).searchBooks(fakeQuery)
            verifyNoMoreInteractions(bookRepository)
            val result = awaitItem()
            result shouldBe BookDataResult.Error
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `get should return empty if the repository returns empty`() = runTest {
        // when
        whenever(bookRepository.searchBooks(fakeQuery)) doReturn flowOf(BookDataResult.Empty)

        // then
        subject(fakeQuery).test {
            verify(bookRepository).searchBooks(fakeQuery)
            verifyNoMoreInteractions(bookRepository)
            val result = awaitItem()
            result shouldBe BookDataResult.Empty
            cancelAndIgnoreRemainingEvents()
        }
    }
}

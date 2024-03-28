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

    private val fakeQuery = "fakeQuery"

    @Test
    fun `get should return book list if the repository returns the book list`() = runBlocking {
        val bookList = listOf(Book("", listOf(), ""))
        whenever(bookRepository.getBooks(fakeQuery)) doReturn BookDataResult.Success(bookList)
        val result = subject.get(fakeQuery)
        verify(bookRepository).getBooks(fakeQuery)
        verifyNoMoreInteractions(bookRepository)
        result shouldBe BookDataResult.Success(bookList)
    }


    @Test
    fun `get should return error if the repository returns error`() = runBlocking {
        whenever(bookRepository.getBooks(fakeQuery)) doReturn BookDataResult.Error("error")
        val result = subject.get(fakeQuery)
        verify(bookRepository).getBooks(fakeQuery)
        verifyNoMoreInteractions(bookRepository)
        result shouldBe BookDataResult.Error("error")
    }


    @Test
    fun `get should return empty if the repository returns empty`() = runBlocking {
        whenever(bookRepository.getBooks(fakeQuery)) doReturn BookDataResult.Empty
        val result = subject.get(fakeQuery)
        verify(bookRepository).getBooks(fakeQuery)
        verifyNoMoreInteractions(bookRepository)
        result shouldBe BookDataResult.Empty
    }
}

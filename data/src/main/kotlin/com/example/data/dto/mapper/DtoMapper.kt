package com.example.data.dto.mapper

import com.example.data.dto.SearchBookResponse
import com.example.domain.model.Book

internal fun SearchBookResponse.toBookList(): List<Book> {
    val list = arrayListOf<Book>()
    items.forEach { item ->
        val book = Book(
            title = item.volumeInfo?.title ?: "unknown",
            authors = item.volumeInfo?.authors ?: emptyList(),
            imageUrl = DtoMapperHelper.getImageUrl(item.volumeInfo?.imageLinks?.thumbnail),
        )
        list.add(book)
    }
    return list
}

package com.example.data.dto.mapper

import com.example.data.dto.SearchBookResponse
import com.example.domain.model.Book

internal fun SearchBookResponse.toBookList(): List<Book> {
    val list = arrayListOf<Book>()
    items.forEach { item ->
        val book = Book(
            title = item.volumeInfo?.title ?: "unknown",
            authors = item.volumeInfo?.authors ?: emptyList(),
            imageUrl = item.volumeInfo?.imageLinks?.thumbnail.toHttpsUrl(),
        )
        list.add(book)
    }
    return list
}

private fun String?.toHttpsUrl(): String? =
    if (this?.contains("https", ignoreCase = true) == false) {
        replace("http", "https")
    } else {
        this
    }

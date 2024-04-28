package com.example.data.model.mapper

import com.example.data.model.Book
import com.example.data.network.model.Item
import com.example.data.network.model.SearchResult

internal fun SearchResult.toBookList(): List<Book> {
    val list = arrayListOf<Book>()
    items.forEach { item ->
        list.add(item.toBook())
    }
    return list
}

private fun Item.toBook(): Book = Book(
    title = volumeInfo?.title ?: "unknown",
    authors = volumeInfo?.authors ?: emptyList(),
    imageUrl = volumeInfo?.imageLinks?.thumbnail.toHttpsUrl(),
)

private fun String?.toHttpsUrl(): String? =
    if (this?.contains("https", ignoreCase = true) == false) {
        replace("http", "https")
    } else {
        this
    }

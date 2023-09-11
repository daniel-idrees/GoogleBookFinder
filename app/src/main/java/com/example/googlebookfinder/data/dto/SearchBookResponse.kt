package com.example.googlebookfinder.data.dto

import com.example.googlebookfinder.domain.model.Book

data class SearchBookResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<Item>,
)

fun SearchBookResponse.map(): List<Book> {
    val list = arrayListOf<Book>()
    items.forEach { item ->
        val book = Book(
            title = item.volumeInfo?.title,
            authors = item.volumeInfo?.authors,
            imageUrl = item.volumeInfo?.imageLinks?.thumbnail,
        )
        list.add(book)
    }
    return list
}

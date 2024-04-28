package com.example.data.utils

import com.example.data.model.Book
import com.example.data.network.model.ImageLinks
import com.example.data.network.model.Item
import com.example.data.network.model.SearchResult
import com.example.data.network.model.VolumeInfo

internal object FakeObjects {
    val queryString = "fakeQuery"

    val bookTitle = "fakeTitle"
    val authorList = listOf("fakeAuthor")
    val thumbnailUrlWithHttp = "http://fakeUrl"
    val thumbnailUrlWithHttps = "https://fakeUrl"

    val fakeResponse = SearchResult(
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

    val fakeBookList = listOf(Book(bookTitle, authorList, thumbnailUrlWithHttps))

    val emptySearchResult = SearchResult(
        "",
        1,
        emptyList(),
    )
}

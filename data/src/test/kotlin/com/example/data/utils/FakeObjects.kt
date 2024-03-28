package com.example.data.utils

import com.example.data.dto.ImageLinks
import com.example.data.dto.Item
import com.example.data.dto.SearchBookResponse
import com.example.data.dto.VolumeInfo
import com.example.domain.model.Book

internal object FakeObjects {
    val queryString = "fakeQuery"

    val bookTitle = "fakeTitle"
    val authorList = listOf("fakeAuthor")
    val thumbnailUrlWithHttp = "http://fakeUrl"
    val thumbnailUrlWithHttps = "https://fakeUrl"

    val fakeResponse = SearchBookResponse(
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

    val emptySearchBookResponse = SearchBookResponse(
        "",
        1,
        emptyList(),
    )
}

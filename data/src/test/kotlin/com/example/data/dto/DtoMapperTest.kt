package com.example.data.dto

import com.example.data.model.Book
import com.example.data.model.mapper.toBookList
import com.example.data.network.model.ImageLinks
import com.example.data.network.model.Item
import com.example.data.network.model.VolumeInfo
import com.example.data.utils.FakeObjects.authorList
import com.example.data.utils.FakeObjects.bookTitle
import com.example.data.utils.FakeObjects.fakeBookList
import com.example.data.utils.FakeObjects.fakeResponse
import com.example.data.utils.FakeObjects.thumbnailUrlWithHttps
import io.kotest.matchers.shouldBe
import org.junit.Test

class DtoMapperTest {
    @Test
    fun `Dto should be mapped correctly to domain model`() {
        val response = fakeResponse
        val result = response.toBookList()
        val expected = fakeBookList
        result shouldBe expected
    }

    @Test
    fun `Dto should be mapped with thumbnail unchanged if it already has https`() {
        val response = fakeResponse.copy(
            items = listOf(
                Item(
                    volumeInfo = VolumeInfo(
                        title = bookTitle,
                        authors = authorList,
                        imageLinks = ImageLinks(thumbnail = thumbnailUrlWithHttps),
                    )
                )
            )
        )
        val expected = fakeBookList

        val result = response.toBookList()

        result shouldBe expected
    }

    @Test
    fun `Dto should be mapped with thumbnail null if it was null in the response`() {
        val response = fakeResponse.copy(
            items = listOf(
                Item(
                    volumeInfo = VolumeInfo(
                        title = bookTitle,
                        authors = authorList,
                        imageLinks = ImageLinks(thumbnail = null),
                    )
                )
            )
        )
        val expected = listOf(
            Book(
                title = bookTitle,
                authors = authorList,
                imageUrl = null
            )
        )

        val result = response.toBookList()

        result shouldBe expected
    }
}

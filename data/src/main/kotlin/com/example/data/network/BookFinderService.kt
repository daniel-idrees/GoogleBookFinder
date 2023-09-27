package com.example.data.network

import com.example.data.dto.SearchBookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookFinderService {
    @GET("v1/volumes")
    suspend fun getBookList(
        @Query("q") q: String,
    ): SearchBookResponse
}

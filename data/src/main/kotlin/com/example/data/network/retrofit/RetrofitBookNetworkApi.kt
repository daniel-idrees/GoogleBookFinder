package com.example.data.network.retrofit

import com.example.core.util.runSuspendCatching
import com.example.data.model.BookDataResult
import com.example.data.model.mapper.toBookList
import com.example.data.network.BookNetworkDataSource
import com.example.data.network.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

internal interface RetrofitBookNetworkApi {
    @GET("v1/volumes")
    suspend fun searchBooks(
        @Query("q") q: String,
    ): SearchResult
}

@Singleton
internal class RetrofitBookNetwork @Inject constructor(
    private val networkApi: RetrofitBookNetworkApi
) : BookNetworkDataSource {
    override suspend fun searchBooks(searchQuery: String): SearchResult = networkApi.searchBooks(searchQuery)
}

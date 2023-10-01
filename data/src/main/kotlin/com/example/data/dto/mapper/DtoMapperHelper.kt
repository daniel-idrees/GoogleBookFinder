package com.example.data.dto.mapper

internal object DtoMapperHelper {

    fun getImageUrl(url: String?): String? =
        if (url?.contains("https", ignoreCase = true) == false) {
            url.replace("http", "https")
        } else {
            url
        }
}

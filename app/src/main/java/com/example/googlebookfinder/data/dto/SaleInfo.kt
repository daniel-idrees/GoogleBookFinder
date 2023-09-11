package com.example.googlebookfinder.data.dto

data class SaleInfo(
    val country: String,
    val saleability: String,
    val isEbook: Boolean,
    val buyLink: String,
    val listPrice: SaleInfoListPrice? = null,
    val retailPrice: SaleInfoListPrice? = null,
    val offers: List<Offer>? = null,
)

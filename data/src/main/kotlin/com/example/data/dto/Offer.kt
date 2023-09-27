package com.example.data.dto

data class Offer(
    val finskyOfferType: Long,
    val listPrice: OfferListPrice,
    val retailPrice: OfferListPrice,
    val giftable: Boolean,
)

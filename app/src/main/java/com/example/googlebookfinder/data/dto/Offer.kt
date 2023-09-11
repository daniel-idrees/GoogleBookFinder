package com.example.googlebookfinder.data.dto

data class Offer(
    val finskyOfferType: Long,
    val listPrice: OfferListPrice,
    val retailPrice: OfferListPrice,
    val giftable: Boolean,
)

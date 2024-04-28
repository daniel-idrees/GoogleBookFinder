package com.example.data.network.model

internal data class Offer(
    val finskyOfferType: Long,
    val listPrice: OfferListPrice,
    val retailPrice: OfferListPrice,
    val giftable: Boolean,
)

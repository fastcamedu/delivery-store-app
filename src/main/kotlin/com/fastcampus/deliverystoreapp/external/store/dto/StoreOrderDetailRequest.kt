package com.fastcampus.deliverystoreapp.external.store.dto

data class StoreOrderDetailRequest(
    val orderId: Long,
    val storeId: Long,
    val accessToken: String,
)

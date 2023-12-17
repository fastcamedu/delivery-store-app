package com.fastcampus.deliverystoreapp.external.store.dto

import com.fastcampus.deliverystoreapp.domain.order.RequestStoreOrderStatus

data class StoreOrderStatusResponse(
    val storeId: Long,
    val orderShortenId: String,
    val changedRequestStoreOrderStatus: RequestStoreOrderStatus,
    val accessToken: String
)

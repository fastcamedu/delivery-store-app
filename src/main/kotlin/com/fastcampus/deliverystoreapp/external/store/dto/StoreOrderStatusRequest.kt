package com.fastcampus.deliverystoreapp.external.store.dto

import com.fastcampus.deliverystoreapp.domain.order.RequestStoreOrderStatus

data class StoreOrderStatusRequest(
    val storeId: Long,
    val orderId: Long,
    val requestStoreOrderStatus: RequestStoreOrderStatus,
    val accessToken: String
)

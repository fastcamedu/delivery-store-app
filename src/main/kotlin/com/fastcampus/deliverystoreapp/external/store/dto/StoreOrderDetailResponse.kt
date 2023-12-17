package com.fastcampus.deliverystoreapp.external.store.dto


data class StoreOrderDetailResponse(
    val orderId: Long,
    val orderShortenId: String,
    val orderUUID: String,
    val storeId: Long,
    val customerId: Long,
    val orderItems: List<OrderItemDTO>
)

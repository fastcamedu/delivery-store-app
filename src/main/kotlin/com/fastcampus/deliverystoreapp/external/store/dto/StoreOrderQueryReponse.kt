package com.fastcampus.deliverystoreapp.external.store.dto

data class StoreOrderQueryResponse(
    val storeId: Long,
    val orders: List<DeliveryOrderDTO>
)

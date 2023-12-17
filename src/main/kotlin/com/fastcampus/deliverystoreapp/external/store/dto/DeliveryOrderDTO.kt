package com.fastcampus.deliverystoreapp.external.store.dto

import com.fastcampus.deliverystoreapp.domain.order.StoreOrderStatus
import java.math.BigDecimal

data class DeliveryOrderDTO(
    val orderId: Long,
    val orderShortenId: String,
    val storeOrderStatus: StoreOrderStatus,
    val storeId: Long,
    val customerId: Long,
    val totalAmount: BigDecimal,
    var orderItems: List<OrderItemDTO> = emptyList(),
    var orderItemSummary: String?,
    var orderItemQuantitySummary: String?,
)

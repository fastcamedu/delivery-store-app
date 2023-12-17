package com.fastcampus.deliverystoreapp.external.store.dto

class OrderItemDTO(
    val orderId: Long,
    val orderItemId: Long,
    val menuId: String,
    val menuName: String,
    val menuQuantity: Int,
)
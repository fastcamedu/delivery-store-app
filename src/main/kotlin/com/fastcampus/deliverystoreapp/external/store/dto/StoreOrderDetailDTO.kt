package com.fastcampus.deliverystoreapp.external.store.dto

data class StoreOrderDetailDTO(
    val orderId: Long,
    val orderShortenId: String,
    val orderUUID: String,
    val storeId: Long,
    val customerId: Long,
    val orderItems: List<OrderItemDTO>
) {
    companion object {
        fun of(response: StoreOrderDetailResponse): StoreOrderDetailDTO {
            return StoreOrderDetailDTO(
                orderId = response.orderId,
                orderShortenId = response.orderShortenId,
                orderUUID = response.orderUUID,
                storeId = response.storeId,
                customerId = response.customerId,
                orderItems = response.orderItems,

            )
        }
    }
}

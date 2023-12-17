package com.fastcampus.deliverystoreapp.external.store.dto

import com.fastcampus.deliverystoreapp.domain.order.RequestStoreOrderStatus
import java.time.LocalDate

data class StoreOrderQueryRequest(
    val storeId: Long,
    val requestStoreOrderStatus: RequestStoreOrderStatus,
    val accessToken: String,
    val queryBaseDate: LocalDate,
)
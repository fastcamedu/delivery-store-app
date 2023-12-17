package com.fastcampus.deliverystoreapp.contoller.order

import com.fastcampus.deliverystoreapp.common.http.HttpConstants
import com.fastcampus.deliverystoreapp.domain.order.RequestStoreOrderStatus
import com.fastcampus.deliverystoreapp.external.store.StoreOrderRequestAdapter
import com.fastcampus.deliverystoreapp.external.store.dto.DeliveryOrderDTO
import com.fastcampus.deliverystoreapp.external.store.dto.StoreOrderDetailRequest
import com.fastcampus.deliverystoreapp.external.store.dto.StoreOrderQueryRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.text.DecimalFormat
import java.time.LocalDate

/**
 * 상점에 들어온 주문을 조회하는 컨트롤러
 */
@Controller
class StoreOrderRequestController(
    private val storeOrderRequestAdapter: StoreOrderRequestAdapter,
) {
    companion object {
        private val logger = KotlinLogging.logger {  }
        private const val ONE_DAY = 1L
    }

    @GetMapping("/orders", "", "/")
    fun list(
        @CookieValue(value = HttpConstants.COOKIE_NAME_STORE_ID) storeId: Long?,
        @CookieValue(value = HttpConstants.COOKIE_NAME_ACCESS_TOKEN) accessToken: String?,
        @RequestParam(value = "requestStoreOrderStatus") requestStoreOrderStatus: RequestStoreOrderStatus?,
        @RequestParam(value = "queryBaseDate") queryBaseDate: LocalDate?,
        model: Model
    ): String {
        logger.info { ">>> 상점에 들어온 주문 정보 조회: $storeId, $requestStoreOrderStatus, $queryBaseDate" }

        if (storeId == null || accessToken == null) {
            return "redirect:/login"
        }

        val setQueryBaseDate = queryBaseDate ?: LocalDate.now()
        val setRequestStoreOrderStatus = requestStoreOrderStatus ?: RequestStoreOrderStatus.READY
        val storeOrderQueryRequest = StoreOrderQueryRequest(
            storeId = storeId,
            requestStoreOrderStatus = setRequestStoreOrderStatus,
            accessToken = accessToken,
            queryBaseDate = setQueryBaseDate,
        )
        val response = storeOrderRequestAdapter.orders(storeOrderQueryRequest)

        buildOrderItemSummary(response.orders)

        model.addAttribute("storeId", response.storeId)
        model.addAttribute("storeOrders", response.orders)
        model.addAttribute("requestStoreOrderStatus", setRequestStoreOrderStatus.name)
        model.addAttribute("queryBaseDate", setQueryBaseDate)
        model.addAttribute("preQueryBaseDate", setQueryBaseDate.minusDays(ONE_DAY))
        model.addAttribute("nextQueryBaseDate", setQueryBaseDate.plusDays(ONE_DAY))

        return "order/orders"
    }

    private fun buildOrderItemSummary(orders: List<DeliveryOrderDTO>) {
        orders.forEach { deliveryOrderDTO ->
            val formatTotalAmount = DecimalFormat("#,###").format(deliveryOrderDTO.totalAmount)
            deliveryOrderDTO.orderItemSummary = "메뉴: ${deliveryOrderDTO.orderItems.count()}개, 가격: ${formatTotalAmount}원"
            deliveryOrderDTO.orderItemQuantitySummary = deliveryOrderDTO.orderItems.joinToString(separator = ",") { "${it.menuName} X ${it.menuQuantity}" }
        }
    }

    @GetMapping("/orders/{orderId}")
    fun detail(
        @CookieValue(value = HttpConstants.COOKIE_NAME_STORE_ID) storeId: Long?,
        @CookieValue(value = HttpConstants.COOKIE_NAME_ACCESS_TOKEN) accessToken: String?,
        @PathVariable("orderId") orderId: Long,
        model: Model
    ): String {
        logger.info { ">>> 상점에 들어온 주문 정보 조회"}
        if (storeId == null || accessToken == null) {
            return "redirect:/login"
        }

        val request = StoreOrderDetailRequest(
            storeId = storeId,
            orderId = orderId,
            accessToken = accessToken,
        )
        val storeOrderDetail = storeOrderRequestAdapter.detail(request)
        model.addAttribute("storeOrderDetail", storeOrderDetail)
        return "order/detail"
    }
}
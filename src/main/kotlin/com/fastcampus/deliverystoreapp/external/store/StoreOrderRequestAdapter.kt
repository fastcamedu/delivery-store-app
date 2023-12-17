package com.fastcampus.deliverystoreapp.external.store

import com.fastcampus.deliverystoreapp.common.http.ExternalHttpApiUtils
import com.fastcampus.deliverystoreapp.exception.NotFoundException
import com.fastcampus.deliverystoreapp.external.store.dto.StoreOrderDetailDTO
import com.fastcampus.deliverystoreapp.external.store.dto.StoreOrderDetailRequest
import com.fastcampus.deliverystoreapp.external.store.dto.StoreOrderDetailResponse
import com.fastcampus.deliverystoreapp.external.store.dto.StoreOrderQueryRequest
import com.fastcampus.deliverystoreapp.external.store.dto.StoreOrderQueryResponse
import com.fastcampus.deliverystoreapp.external.store.dto.StoreOrderStatusRequest
import com.fastcampus.deliverystoreapp.external.store.dto.StoreOrderStatusResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class StoreOrderRequestAdapter(
    private val restTemplate: RestTemplate,
) {

    @Value("\${external.apis.delivery-store-api.host}")
    private lateinit var storeApiHost: String

    @Value("\${external.apis.delivery-store-api.path.order-requests}")
    private lateinit var orderRequestsApiPath: String

    @Value("\${external.apis.delivery-store-api.path.order-detail}")
    private lateinit var orderDetailApiPath: String

    @Value("\${external.apis.delivery-store-api.path.order-status}")
    private lateinit var orderStatusApiPath: String

    companion object {
        private val logger = KotlinLogging.logger {  }

        private const val STORE_ID = "storeId"
        private const val REQUEST_STORE_ORDER_STATUS = "requestStoreOrderStatus"
        private const val QUERY_BASE_DATE = "queryBaseDate"
    }

    fun orders(request: StoreOrderQueryRequest): StoreOrderQueryResponse {

        val storeOrderApiFullPath = UriComponentsBuilder.fromHttpUrl(
            String.format("$storeApiHost$orderRequestsApiPath", request.storeId)
        )
            .queryParam(STORE_ID, request.storeId.toString())
            .queryParam(REQUEST_STORE_ORDER_STATUS, request.requestStoreOrderStatus.name)
            .queryParam(QUERY_BASE_DATE, request.queryBaseDate.toString())
            .build().toUriString()
        logger.info { ">>> 고객 주문 목록 조회: ${request.storeId}, ${request.requestStoreOrderStatus}, $storeOrderApiFullPath" }

        val headers = ExternalHttpApiUtils.getApiHeaderWithAuthorization(request.accessToken)
        val httpBody = LinkedMultiValueMap<String, String>()
        val request = HttpEntity(httpBody, headers)

        val responseEntity =
            restTemplate.exchange(storeOrderApiFullPath, HttpMethod.GET, request, StoreOrderQueryResponse::class.java)

        logger.info { ">>> 고객 주문 목록 조회 결과: ${responseEntity.body}" }
        return responseEntity.body ?: throw NotFoundException("상점이 요청받은 주문 정보를 찾을 수 없습니다.")
    }

    fun detail(request: StoreOrderDetailRequest): StoreOrderDetailDTO {
        val storeOrderApiFullPath = String.format("$storeApiHost$orderDetailApiPath}", request.storeId, request.orderId)

        val headers = ExternalHttpApiUtils.getApiHeaderWithAuthorization(request.accessToken)

        val httpBody = LinkedMultiValueMap<String, String>()
        httpBody.add(STORE_ID, request.storeId.toString())

        val request = HttpEntity(httpBody, headers)

        val responseEntity =
            restTemplate.exchange(storeOrderApiFullPath, HttpMethod.GET, request, StoreOrderDetailResponse::class.java)

        val storeOrderDetailResponse = responseEntity.body ?: throw NotFoundException("선택하신 주문 정보를 찾을 수 없습니다.")
        return StoreOrderDetailDTO.of(storeOrderDetailResponse)
    }

    fun command(request: StoreOrderStatusRequest): StoreOrderStatusResponse {
        val storeOrderStatusApiFullPath = String.format("$storeApiHost$orderStatusApiPath}", request.storeId, request.orderId)

        val headers = ExternalHttpApiUtils.getApiHeaderWithAuthorization(request.accessToken)

        val httpBody = LinkedMultiValueMap<String, String>()
        httpBody.add(STORE_ID, request.storeId.toString())

        val request = HttpEntity(httpBody, headers)

        val responseEntity =
            restTemplate.exchange(storeOrderStatusApiFullPath, HttpMethod.GET, request, StoreOrderStatusResponse::class.java)
        return responseEntity.body ?: throw NotFoundException("선택하신 주문 정보를 찾을 수 없습니다.")
    }
}
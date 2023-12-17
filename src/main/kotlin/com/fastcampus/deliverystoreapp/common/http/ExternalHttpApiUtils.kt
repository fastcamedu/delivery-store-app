package com.fastcampus.deliverystoreapp.common.http

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

/**
 * 외부 API 호출 시 사용하는 헤더 생성 유틸리티 클래스
 */
class ExternalHttpApiUtils {
    companion object {
        fun getApiHeaderWithAuthorization(token: String? = ""): HttpHeaders {
            val httpHeaders = HttpHeaders()
            httpHeaders.contentType = MediaType.APPLICATION_JSON
            httpHeaders.add(HttpConstants.HEADER_AUTHORIZATION, "Bearer $token")
            return httpHeaders
        }
    }
}
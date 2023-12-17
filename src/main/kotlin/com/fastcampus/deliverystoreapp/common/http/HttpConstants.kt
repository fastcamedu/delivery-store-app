package com.fastcampus.deliverystoreapp.common.http

/**
 * HTTP 헤더, 쿠키 등의 상수를 정의하는 클래스
 */
class HttpConstants {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val COOKIE_NAME_ACCESS_TOKEN = "AccessToken"
        const val COOKIE_NAME_STORE_ID = "storeId"
    }
}
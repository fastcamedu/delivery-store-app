package com.fastcampus.deliverystoreapp.common.http

import jakarta.servlet.http.Cookie

/**
 * 쿠키를 생성하는 유틸리티 클래스
 */
class CookieUtils {
    companion object {
        fun createCookie(
            cookieKey: String,
            cookieValue: String,
            cookiePath: String = "/",
            cookieMaxAge: Int = 60 * 60 * 24 * 7
        ): Cookie {
            val cookie = Cookie(cookieKey, cookieValue)
            cookie.path = cookiePath
            cookie.maxAge = cookieMaxAge
            return cookie
        }
    }
}
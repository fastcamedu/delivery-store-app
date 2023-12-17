package com.fastcampus.deliverystoreapp.interceptor

import com.fastcampus.deliverystoreapp.common.http.HttpConstants
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor

/**
 * 인증 토큰을 검사하는 인터셉터
 * - 인증 토큰이 없으면 로그인 페이지로 리다이렉트
 */
class AuthTokenInterceptor: HandlerInterceptor {

    companion object {
        private const val UNKNOWN_USER = "undefined"

        private val logger = KotlinLogging.logger {  }
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.debug { ">>> LoginInterceptor.preHandle" }
        if (hasAuthToken(request)) {
            logger.info { ">>> 인증 토큰 보유,  상점 사장님" }
            return super.preHandle(request, response, handler)
        }

        logger.info { ">>> 인증 토큰 미소유, 상점 사장님" }
        response.sendRedirect("/login")
        return false
    }

    private fun hasAuthToken(request: HttpServletRequest): Boolean {
        val accessToken = request.cookies?.firstOrNull { it.name == HttpConstants.COOKIE_NAME_ACCESS_TOKEN }?.value
        val storeId = request.cookies?.firstOrNull { it.name == HttpConstants.COOKIE_NAME_STORE_ID }?.value
        if (accessToken == null || storeId == null || UNKNOWN_USER == accessToken || UNKNOWN_USER == storeId) {
            return false
        }
        return true
    }
}
package com.fastcampus.deliverystoreapp.config

import com.fastcampus.deliverystoreapp.interceptor.AuthTokenInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * WebMvcConfigurer: 웹 MVC 설정을 위한 인터페이스
 */
@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(AuthTokenInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/error", "/hello",
                "/favicon.ico",
                "/login/**",
                "/js/**", "/css/**", "/images/**"
            )
    }
}
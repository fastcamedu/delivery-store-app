package com.fastcampus.deliverystoreapp.external.login

import com.fastcampus.deliverystoreapp.external.login.dto.LoginResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
 * 상점 로그인 어댑터 클래스
 * - 상점 API와 통신을 통해서 로그인 처리
 */
@Component
class StoreLoginAdapter(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
) {
    @Value("\${external.apis.delivery-store-api.host}")
    private lateinit var deliveryStoreApiUrl: String

    @Value("\${external.apis.delivery-store-api.path.login}")
    private lateinit var loginPath: String

    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    fun login(email: String, password: String): LoginResponse? {
        val loginFullPath = "$deliveryStoreApiUrl$loginPath"
        logger.info { ">>> 사장님 로그인 요청, email: $email, url: $loginFullPath" }

        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val loginRequestParam = JSONObject()

        loginRequestParam.put("email", email)
        loginRequestParam.put("password", password)

        val request = HttpEntity(loginRequestParam.toString(), httpHeaders)

        val responseEntity = restTemplate.postForEntity(loginFullPath, request, LoginResponse::class.java)
        return responseEntity.body
    }
}
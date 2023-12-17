package com.fastcampus.deliverystoreapp.external.login.dto

data class LoginResponse(
    val storeId: Long,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
)

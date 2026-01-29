package com.linarc.kmmpoc

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val name: String,
    val token: String,
    val role: String
)

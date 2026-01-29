package com.linarc.kmmpoc

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {
    private val httpClient = HttpClient(defaultEngine()) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                prettyPrint = true
            })
        }
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return httpClient.post("https://49d2e2c3-4b02-4c43-abf9-03b4575ff72e.mock.pstmn.io/v1/login/") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}

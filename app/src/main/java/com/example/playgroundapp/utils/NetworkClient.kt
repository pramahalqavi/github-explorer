package com.example.playgroundapp.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NetworkClient {

    val client: HttpClient
    get() = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
        engine {
            endpoint {
                connectTimeout = 30_000
            }
        }
    }

    suspend inline fun <reified T> get(url: String): T {
        return client.get(url).body()
    }

    fun close() {
        client.close()
    }
}
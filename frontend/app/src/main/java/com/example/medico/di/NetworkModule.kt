package com.example.medico.di

import android.util.Log
import com.example.medico.utils.SharedPreferencesManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        val prefs: SharedPreferencesManager = get()
        Log.d("TOKEN", prefs.getJwtToken().toString())
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val token = prefs.getJwtToken().orEmpty()
                        Log.d("BearerToken", "Using token: $token")
                        BearerTokens(
                            accessToken = token,
                            refreshToken = ""
                        )
                    }
                    sendWithoutRequest { request ->
                        val path = request.url.encodedPath
                        !(
                                path.endsWith("/login") ||
                                        path.endsWith("/doctor/register") ||
                                        path.endsWith("/u/register")
                                )
                    }
                }
            }
        }
    }
}
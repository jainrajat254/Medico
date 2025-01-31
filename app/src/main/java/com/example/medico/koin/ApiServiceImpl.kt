package com.example.medico.koin

import com.example.medico.data.LoginCredentials
import com.example.medico.data.LoginResponse
import com.example.medico.data.UserData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiServiceImpl(private val client: HttpClient) : ApiService {

    private val url = "https://dda1-2409-40d2-1014-7973-6d86-615c-659e-c3cc.ngrok-free.app"

    override suspend fun login(user: LoginCredentials): LoginResponse {
        return try {
            val response: LoginResponse = client.post("$url/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginCredentials(user.email,user.password)) // Sending LoginCredentials data
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during login: ${e.message}") // Clear error message
        }
    }

    override suspend fun register(data: UserData): UserData {
        return try {
            val response: UserData = client.post("$url/register") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during registration: ${e.message}")
        }
    }
}

package com.example.medico.koin

import com.example.medico.data.DoctorRegister
import com.example.medico.data.DoctorResponse
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

    private val url = "https://dcbd-2409-40d2-19-ab43-5d90-4c8d-b45c-eed1.ngrok-free.app"

    override suspend fun login(user: LoginCredentials): LoginResponse {
        return try {
            val response: LoginResponse = client.post("$url/doctor/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginCredentials(user.email,user.password, user.role))
            println(user.role)// Sending LoginCredentials data
            }.body()
            response

        } catch (e: Exception) {
            throw Exception("Error during login: ${e.message}") // Clear error message
        }
    }

    override suspend fun registerDoc(data: UserData): UserData {
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

    override suspend fun registerDoc(data: DoctorRegister): DoctorRegister {
        return try {
            val response: DoctorRegister = client.post("$url/doctor/register") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during registration: ${e.message}")
        }
    }

    override suspend fun loginDoc(user: LoginCredentials): DoctorResponse {
        return try {
            val response: DoctorResponse = client.post("$url/doctor/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginCredentials(user.email,user.password,user.role)) // Sending LoginCredentials data
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during login: ${e.message}") // Clear error message
        }
    }
}

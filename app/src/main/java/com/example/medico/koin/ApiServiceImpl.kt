package com.example.medico.koin

import android.util.Log
import com.example.medico.data.DocAddressDetailsDTO
import com.example.medico.data.DocMedicalDetailsDTO
import com.example.medico.data.DoctorRegister
import com.example.medico.data.DoctorResponse
import com.example.medico.data.EditDocDTO
import com.example.medico.data.EditUserDTO
import com.example.medico.data.LoginCredentials
import com.example.medico.data.LoginResponse
import com.example.medico.data.PasswordUpdateRequest
import com.example.medico.data.UserData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiServiceImpl(private val client: HttpClient) : ApiService {

    private val url = "https://b29d-2409-40d2-10b6-99f5-65ea-417d-7570-5412.ngrok-free.app"

    override suspend fun login(user: LoginCredentials): LoginResponse {
        return try {
            val response: LoginResponse = client.post("$url/doctor/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginCredentials(user.email, user.password, user.role))
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
                setBody(
                    LoginCredentials(
                        user.email,
                        user.password,
                        user.role
                    )
                )
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during login: ${e.message}")
        }
    }

    override suspend fun editDetails(data: EditUserDTO, id: String): Result<UserData> {
        return try {
            val response: UserData = client.put("$url/u/editDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editDocPersonalDetails(
        data: EditDocDTO,
        id: String,
    ): Result<DoctorResponse> {
        return try {
            val response: DoctorResponse = client.put("$url/doctor/editDocPersonalDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editPassword(data: PasswordUpdateRequest, id: String): Result<UserData> {
        return try {
            val response: UserData = client.put("$url/u/editPassword/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editDocAddressDetails(
        data: DocAddressDetailsDTO,
        id: String,
    ): Result<DoctorResponse> {
        return try {
            val response: DoctorResponse = client.put("$url/doctor/editDocAddressDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editDocMedicalDetails(
        data: DocMedicalDetailsDTO,
        id: String,
    ): Result<DoctorResponse> {
        return try {
            val response: DoctorResponse = client.put("$url/doctor/editDocMedicalDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

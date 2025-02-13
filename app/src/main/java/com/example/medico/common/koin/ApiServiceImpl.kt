package com.example.medico.common.koin

import android.util.Log
import com.example.medico.doctor.dto.EditDocAddressDetails
import com.example.medico.doctor.dto.EditDocMedicalDetails
import com.example.medico.doctor.model.DoctorDetails
import com.example.medico.doctor.responses.DoctorLoginResponse
import com.example.medico.doctor.dto.EditDocPersonalDetails
import com.example.medico.user.dto.EditUserPersonalDetails
import com.example.medico.common.model.LoginCredentials
import com.example.medico.user.responses.UserLoginResponse
import com.example.medico.common.dto.EditPassword
import com.example.medico.user.data.UserDetails
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiServiceImpl(private val client: HttpClient) : ApiService {

    private val url = "https://b29d-2409-40d2-10b6-99f5-65ea-417d-7570-5412.ngrok-free.app"

    override suspend fun login(user: LoginCredentials): UserLoginResponse {
        return try {
            val response: UserLoginResponse = client.post("$url/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginCredentials(user.email, user.password, user.role))
                println(user.role)// Sending LoginCredentials data
            }.body()
            response

        } catch (e: Exception) {
            throw Exception("Error during login: ${e.message}") // Clear error message
        }
    }

    override suspend fun registerDoc(data: UserDetails): UserDetails {
        return try {
            val response: UserDetails = client.post("$url/register") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during registration: ${e.message}")
        }
    }

    override suspend fun registerDoc(data: DoctorDetails): DoctorDetails {
        return try {
            val response: DoctorDetails = client.post("$url/doctor/register") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during registration: ${e.message}")
        }
    }

    override suspend fun loginDoc(user: LoginCredentials): DoctorLoginResponse {
        return try {
            val response: DoctorLoginResponse = client.post("$url/login") {
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

    override suspend fun editDetails(data: EditUserPersonalDetails, id: String): Result<UserDetails> {
        return try {
            val response: UserDetails = client.put("$url/u/editDetails/$id") {
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
        data: EditDocPersonalDetails,
        id: String,
    ): Result<DoctorLoginResponse> {
        return try {
            val response: DoctorLoginResponse = client.put("$url/doctor/editDocPersonalDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editPassword(data: EditPassword, id: String): Result<UserDetails> {
        return try {
            val response: UserDetails = client.put("$url/u/editPassword/$id") {
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
        data: EditDocAddressDetails,
        id: String,
    ): Result<DoctorLoginResponse> {
        return try {
            val response: DoctorLoginResponse = client.put("$url/doctor/editDocAddressDetails/$id") {
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
        data: EditDocMedicalDetails,
        id: String,
    ): Result<DoctorLoginResponse> {
        return try {
            val response: DoctorLoginResponse = client.put("$url/doctor/editDocMedicalDetails/$id") {
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

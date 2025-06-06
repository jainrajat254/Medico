package com.example.medico.data.repository

import com.example.medico.domain.model.DoctorDetails
import com.example.medico.domain.model.DoctorLoginResponse
import com.example.medico.domain.model.LoginCredentials
import com.example.medico.domain.model.UserDetails
import com.example.medico.domain.model.UserLoginResponse
import com.example.medico.domain.repository.AuthRepository
import com.example.medico.domain.service.ApiService

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(loginRequest: LoginCredentials): UserLoginResponse {
        return apiService.login(loginRequest)
    }

    override suspend fun registerUser(data: UserDetails): UserDetails {
        return apiService.registerUser(data)
    }

    override suspend fun registerDoc(data: DoctorDetails): DoctorDetails {
        return apiService.registerDoc(data)
    }

    override suspend fun loginDoc(user: LoginCredentials): DoctorLoginResponse {
        return apiService.loginDoc(user)
    }
}
package com.example.medico.domain.repository

import com.example.medico.domain.model.DoctorDetails
import com.example.medico.domain.model.DoctorLoginResponse
import com.example.medico.domain.model.LoginCredentials
import com.example.medico.domain.model.UserDetails
import com.example.medico.domain.model.UserLoginResponse

interface AuthRepository {

    suspend fun login(loginRequest: LoginCredentials): UserLoginResponse

    suspend fun registerUser(data: UserDetails): UserDetails

    suspend fun loginDoc(user: LoginCredentials): DoctorLoginResponse

    suspend fun registerDoc(data: DoctorDetails): DoctorDetails
}
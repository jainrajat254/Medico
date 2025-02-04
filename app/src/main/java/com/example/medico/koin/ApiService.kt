package com.example.medico.koin

import com.example.medico.data.DoctorData
import com.example.medico.data.DoctorRegister
import com.example.medico.data.LoginCredentials
import com.example.medico.data.LoginResponse
import com.example.medico.data.UserData

interface ApiService {
    suspend fun login(user: LoginCredentials): LoginResponse
    suspend fun register(data: UserData): UserData
    suspend fun register(data : DoctorRegister): DoctorRegister
}

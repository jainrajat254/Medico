package com.example.medico.koin

import com.example.medico.data.DoctorRegister
import com.example.medico.data.DoctorResponse
import com.example.medico.data.EditDocDTO
import com.example.medico.data.EditUserDTO
import com.example.medico.data.LoginCredentials
import com.example.medico.data.LoginResponse
import com.example.medico.data.UserData

interface ApiService {
    suspend fun login(user: LoginCredentials): LoginResponse
    suspend fun registerDoc(data: UserData): UserData
    suspend fun loginDoc(user: LoginCredentials): DoctorResponse
    suspend fun registerDoc(data : DoctorRegister): DoctorRegister
    suspend fun editDetails(data: EditUserDTO, id: String): Result<UserData>
    suspend fun editDocPersonalDetails(data: EditDocDTO, id: String): Result<DoctorResponse>
}

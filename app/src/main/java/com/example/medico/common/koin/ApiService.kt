package com.example.medico.common.koin

import com.example.medico.common.dto.EditPassword
import com.example.medico.common.model.LoginCredentials
import com.example.medico.doctor.dto.DoctorDTO
import com.example.medico.doctor.dto.EditDocAddressDetails
import com.example.medico.doctor.dto.EditDocMedicalDetails
import com.example.medico.doctor.dto.EditDocPersonalDetails
import com.example.medico.doctor.model.DoctorDetails
import com.example.medico.doctor.responses.DoctorLoginResponse
import com.example.medico.user.dto.EditUserPersonalDetails
import com.example.medico.user.dto.UserDTO
import com.example.medico.user.model.ExtraDetails
import com.example.medico.user.model.UserDetails
import com.example.medico.user.responses.UserDetailsResponse
import com.example.medico.user.responses.UserLoginResponse

interface ApiService {
    suspend fun login(user: LoginCredentials): Result<UserLoginResponse>
    suspend fun registerUser(data: UserDetails): UserDetails
    suspend fun loginDoc(user: LoginCredentials): DoctorLoginResponse
    suspend fun registerUser(data : DoctorDetails): DoctorDetails
    suspend fun editDetails(data: EditUserPersonalDetails, id: String): Result<UserDetails>
    suspend fun editDocPersonalDetails(data: EditDocPersonalDetails, id: String): Result<DoctorLoginResponse>
    suspend fun editPassword(data: EditPassword, id: String): Result<UserDetails>
    suspend fun editDocAddressDetails(data: EditDocAddressDetails, id: String): Result<DoctorLoginResponse>
    suspend fun editDocMedicalDetails(data: EditDocMedicalDetails, id: String): Result<DoctorLoginResponse>
    suspend fun addExtraDetails(data: ExtraDetails, id: String): Result<UserDetailsResponse>
    suspend fun getExtraDetails(id: String): Result<UserDetailsResponse>
    suspend fun getPersonalInfoId(id: String): Result<String>
    suspend fun getDoctors() : Result<List<DoctorDTO>>
    suspend fun getDetails(id: String) : Result<UserDTO>
}

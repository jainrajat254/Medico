package com.example.medico.domain.repository

import com.example.medico.domain.model.DoctorDTO
import com.example.medico.domain.model.DoctorLoginResponse
import com.example.medico.domain.model.EditDocAddressDetails
import com.example.medico.domain.model.EditDocMedicalDetails
import com.example.medico.domain.model.EditDocPersonalDetails
import com.example.medico.domain.model.EditPassword
import com.example.medico.domain.model.EditUserPersonalDetails
import com.example.medico.domain.model.ExtraDetails
import com.example.medico.domain.model.GetExtraDetailsId
import com.example.medico.domain.model.UserDTO
import com.example.medico.domain.model.UserDetails
import com.example.medico.domain.model.UserDetailsResponse

interface SettingsRepository {

    suspend fun editDetails(data: EditUserPersonalDetails, id: String): UserDetails

    suspend fun editDocPersonalDetails(
        data: EditDocPersonalDetails,
        id: String,
    ): DoctorLoginResponse

    suspend fun editPassword(data: EditPassword, id: String): String

    suspend fun editDocAddressDetails(
        data: EditDocAddressDetails,
        id: String,
    ): DoctorLoginResponse

    suspend fun editDocMedicalDetails(
        data: EditDocMedicalDetails,
        id: String,
    ): DoctorLoginResponse

    suspend fun addExtraDetails(data: ExtraDetails, id: String): UserDetailsResponse

    suspend fun getExtraDetails(id: String): UserDetailsResponse

    suspend fun getPersonalInfoId(id: String): GetExtraDetailsId

    suspend fun getDoctors(): List<DoctorDTO>

    suspend fun getDetails(id: String): UserDTO
}
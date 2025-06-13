package com.example.medico.data.repository

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
import com.example.medico.domain.repository.SettingsRepository
import com.example.medico.domain.service.ApiService

class SettingsRepositoryImpl(private val apiService: ApiService) : SettingsRepository {

    override suspend fun editDetails(
        data: EditUserPersonalDetails,
        id: String,
    ): UserDetails {
        return apiService.editDetails(data, id)
    }

    override suspend fun editDocPersonalDetails(
        data: EditDocPersonalDetails,
        id: String,
    ): DoctorLoginResponse {
        return apiService.editDocPersonalDetails(data, id)
    }

    override suspend fun editPassword(data: EditPassword, id: String): String {
        return apiService.editPassword(data, id)
    }

    override suspend fun editDocAddressDetails(
        data: EditDocAddressDetails,
        id: String,
    ): DoctorLoginResponse {
        return apiService.editDocAddressDetails(data = data, id = id)
    }

    override suspend fun editDocMedicalDetails(
        data: EditDocMedicalDetails,
        id: String,
    ): DoctorLoginResponse {
        return apiService.editDocMedicalDetails(data, id)
    }

    override suspend fun addExtraDetails(
        data: ExtraDetails,
        id: String,
    ): UserDetailsResponse {
        return apiService.addExtraDetails(data, id)
    }

    override suspend fun getExtraDetails(id: String): UserDetailsResponse {
        return apiService.getExtraDetails(id)
    }

    override suspend fun getPersonalInfoId(id: String): GetExtraDetailsId {
        return apiService.getPersonalInfoId(id)
    }

    override suspend fun getDoctors(): List<DoctorDTO> {
        return apiService.getDoctors()
    }

    override suspend fun getDetails(id: String): UserDTO {
        return apiService.getDetails(id)
    }
}
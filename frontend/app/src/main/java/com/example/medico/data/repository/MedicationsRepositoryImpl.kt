package com.example.medico.data.repository

import com.example.medico.domain.model.MedicationResponse
import com.example.medico.domain.model.Medications
import com.example.medico.domain.model.MedicationsDTO
import com.example.medico.domain.model.OldMedicationsDTO
import com.example.medico.domain.repository.MedicationsRepository
import com.example.medico.domain.service.ApiService

class MedicationsRepositoryImpl(private val apiService: ApiService) : MedicationsRepository {
    override suspend fun addMedications(request: Medications, id: String): Medications {
        return apiService.addMedications(request, id)
    }

    override suspend fun getMedications(id: String): List<MedicationResponse> {
        return apiService.getMedications(id)
    }

    override suspend fun doctorMedication(
        doctorId: String,
        userId: String,
    ): List<MedicationsDTO> {
        return apiService.doctorMedication(doctorId, userId)
    }

    override suspend fun updateMedication(medId: String, data: Medications): Medications {
        return apiService.updateMedication(medId, data)
    }

    override suspend fun removeMedications(medId: String): String {
        return apiService.removeMedications(medId)
    }

    override suspend fun oldMedications(userId: String): List<OldMedicationsDTO> {
        return apiService.oldMedications(userId)
    }
}
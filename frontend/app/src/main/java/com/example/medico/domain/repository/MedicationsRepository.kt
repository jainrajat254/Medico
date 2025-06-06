package com.example.medico.domain.repository

import com.example.medico.domain.model.MedicationResponse
import com.example.medico.domain.model.Medications
import com.example.medico.domain.model.MedicationsDTO
import com.example.medico.domain.model.OldMedicationsDTO

interface MedicationsRepository {

    suspend fun addMedications(request: Medications, id: String): Medications

    suspend fun getMedications(id: String): List<MedicationResponse>

    suspend fun doctorMedication(doctorId: String, userId: String): List<MedicationsDTO>

    suspend fun updateMedication(medId: String, data: Medications): Medications

    suspend fun removeMedications(medId: String): String

    suspend fun oldMedications(userId: String): List<OldMedicationsDTO>

}
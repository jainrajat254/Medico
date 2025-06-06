package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MedicationResponse(
    val dosageType: String,
    val duration: String,
    val frequency: String,
    val id: String,
    val intakeMethod: String,
    val medicationName: String,
    val medicationType: String,
    val time: String? = "N/A",
    val doctorName: String,
    val doctorId: String,
    val startDate: String
)
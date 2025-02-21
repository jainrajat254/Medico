package com.example.medico.user.responses

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
    val time: String? = "N/A"
)
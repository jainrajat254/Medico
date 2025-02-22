package com.example.medico.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class OldMedicationsDTO(
    val id: String,
    val doctorId: String,
    val doctorName: String,
    val startDate: String,
    val medicationName: String,
    val endDate: String,
)
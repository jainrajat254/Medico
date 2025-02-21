package com.example.medico.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class MedicationsDTO(
    val dosageType: String,
    val duration: String,
    val frequency: String,
    val id: String,
    val intakeMethod: String,
    val medicationName: String,
    val medicationType: String,
    val time: String
)
package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Medications(
    val dosageType: String,
    val duration: String,
    val frequency: String,
    val intakeMethod: String,
    val medicationName: String,
    val medicationType: String,
    val time: String? = null,
    val doctorId: String,
    val doctorName: String
)
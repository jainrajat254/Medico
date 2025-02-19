package com.example.medico.user.model

import kotlinx.serialization.Serializable

@Serializable
data class Medications(
    val dosage: String,
    val dosageType: String,
    val duration: String,
    val frequency: String,
    val intakeMethod: String,
    val medicationName: String,
    val medicationType: String,
    val time: String
)
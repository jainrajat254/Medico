package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DoctorDTO(
    val id: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val specialization: String,
    val experience: Int,
    val fee: Int,
    val workspaceName: String,
    val address: String,
    val medicalRegNo: String,
    val qualification: String,
    val state: String,
    val district: String,
    val zipCode: String,
    val phone: String,
    val email: String,
    val workingTime: List<String>,
    val availableForOnlineConsultation: Boolean,
)

package com.example.medico.data

import kotlinx.serialization.Serializable

@Serializable
data class DoctorResponse(
    val address: String,
    val availableForOnlineConsultation: Boolean,
    val district: String,
    val dob: String,
    val email: String,
    val experience: Int,
    val fee: Int,
    val firstName: String,
    val gender: String,
    val id: String,
    val lastName: String,
    val medicalRegNo: String,
    val phone: String,
    val qualification: String,
    val specialization: String,
    val state: String,
    val token: String,
    val uid: String,
    val workingTime: List<String>,
    val workspaceName: String,
    val zipCode: String,
    val password: String
)
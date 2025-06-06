package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DoctorDetails(
    val address: String,
    val availableForOnlineConsultation: Boolean,
    val district: String,
    val dob: String,
    val email: String,
    val experience: Int,
    val fee: Int,
    val firstName: String,
    val gender: String,
    val lastName: String,
    val medicalRegNo: String,
    val password: String,
    val phone: String,
    val qualification: String,
    val specialization: String,
    val state: String,
    val uid: String,
    val workingTime: List<String>,
    val workspaceName: String,
    val zipCode: String
)
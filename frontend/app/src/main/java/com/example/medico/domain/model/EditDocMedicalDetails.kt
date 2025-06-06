package com.example.medico.domain.model

data class EditDocMedicalDetails(
    val medicalRegNo: String,
    val qualification: String,
    val specialization: String,
    val experience: Int,
    val fee: Int,
    val availableForOnlineConsultation: Boolean,
)

package com.example.medico.data

data class DocMedicalDetailsDTO(
    val medicalRegNo: String,
    val qualification: String,
    val specialization: String,
    val experience: Int,
    val fee: Int,
    val availableForOnlineConsultation: Boolean,
)

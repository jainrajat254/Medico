package com.example.medico.common.model

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val gender: String = "",
    val phone: String = "",
    val email: String = "",
    val weight: String = "",
    val height: String = "",
    val bloodGroup: String = "",
    val profileImageUrl: String? = null
)

data class Medication(
    val name: String,
    val dosage: String,
    val instructions: String
)

data class HealthReport(
    val reportName: String,
    val doctorName: String,
    val attention: String
)

data class HealthRecord(
    val recordName: String,
    val doctorName: String,
    val diagnosis: String
)

data class Medicines(
    val name: String,
    val time: String
)


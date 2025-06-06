package com.example.medico.domain.model

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
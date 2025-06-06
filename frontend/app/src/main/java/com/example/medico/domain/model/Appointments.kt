package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Appointments(
    val doctorName: String,
    val patientName: String,
    val specialization: String,
    val workspaceName: String,
    val date: String,
    val doctorId: String,
    val time: String,
    val appointmentBookingTime: String? = null
)

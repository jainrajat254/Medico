package com.example.medico.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentDTO(
    val appointmentBookingTime: String,
    val date: String,
    val patientName: String,
    val time: String,
    val userId: String
)

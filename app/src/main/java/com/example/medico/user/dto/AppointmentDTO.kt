package com.example.medico.user.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentDTO(
    val id: String,
    val appointmentBookingTime: String,
    val date: String,
    val patientName: String,
    val time: String,
    val userId: String,
    @SerialName("appointmentStatus") var status: AppointmentStatus, // Map to backend field
)
 {
    val isCompleted: Boolean
        get() = status == AppointmentStatus.COMPLETED // ✅ This ensures filtering works correctly
}


@Serializable
enum class AppointmentStatus {
    @SerialName("BOOKED") BOOKED,
    @SerialName("COMPLETED") COMPLETED,
    @SerialName("RESCHEDULED") RESCHEDULED
}

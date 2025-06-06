package com.example.medico.domain.model

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
    val queueIndex: Int,
    @SerialName("appointmentStatus") var status: AppointmentStatus,
)



@Serializable
enum class AppointmentStatus {
    @SerialName("BOOKED") BOOKED,
    @SerialName("COMPLETED") COMPLETED,
    @SerialName("ABSENT") ABSENT
}

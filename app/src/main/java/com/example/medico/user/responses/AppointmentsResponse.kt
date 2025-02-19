package com.example.medico.user.responses

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentsResponse(
    val id: String,
    val doctorName: String,
    val date: String,
    val time: String,
    val specialization: String,
    val workspaceName: String
)

package com.example.medico.user.model

import kotlinx.serialization.Serializable

@Serializable
data class Appointments(
    val doctorName: String,
    val specialization: String,
    val workspaceName: String,
    val date: String,
    val time: String,
    val userId: String? = null
)

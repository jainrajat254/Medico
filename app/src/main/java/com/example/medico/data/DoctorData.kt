package com.example.medico.data

import java.util.UUID

data class DoctorData(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String
)

package com.example.medico.data

import kotlinx.serialization.Serializable

@Serializable
data class EditDocDTO(
    val uid: String?,
    val dob: String?,
    val phone: String?,
    val email: String?
)


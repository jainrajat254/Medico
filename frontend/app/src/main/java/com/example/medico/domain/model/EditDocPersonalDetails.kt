package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EditDocPersonalDetails(
    val uid: String?,
    val dob: String?,
    val phone: String?,
    val email: String?
)


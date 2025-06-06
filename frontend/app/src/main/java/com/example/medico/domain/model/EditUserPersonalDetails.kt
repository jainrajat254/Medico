package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EditUserPersonalDetails(
    val age: String?,
    val bloodGroup: String?,
    val phone: String?,
    val email: String?
)


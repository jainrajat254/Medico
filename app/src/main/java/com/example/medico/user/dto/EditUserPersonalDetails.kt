package com.example.medico.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class EditUserPersonalDetails(
    val age: String?,
    val bloodGroup: String?,
    val phone: String?,
    val email: String?
)


package com.example.medico.data

import kotlinx.serialization.Serializable

@Serializable
data class PasswordUpdateRequest(
    val currentPassword: String,
    val newPassword: String
)

package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EditPassword(
    val currentPassword: String,
    val newPassword: String
)

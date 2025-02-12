package com.example.medico.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class EditPassword(
    val currentPassword: String,
    val newPassword: String
)

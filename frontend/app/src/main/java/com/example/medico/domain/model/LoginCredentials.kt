package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentials(
    val email: String,
    val password: String,
    val role: String
)

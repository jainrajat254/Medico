package com.example.medico.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val id: String,
    val firstName: String,
    val lastName: String,
    val age: String,
    val gender: String,
    val bloodGroup: String,
    val phone: String,
    val email: String,
    val password: String
)

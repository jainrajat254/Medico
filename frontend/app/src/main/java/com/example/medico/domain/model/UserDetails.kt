package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val firstName: String,
    val lastName: String,
    val age: String,
    val gender: String,
    val bloodGroup: String,
    val phone: String,
    val email: String,
    val password: String
)

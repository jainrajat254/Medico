package com.example.medico.data

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val firstName: String,
    val lastName: String,
    val age: String,
    val gender: String,
    val bloodGroup: String,
    val phone: String,
    val email: String,
    val password: String
)

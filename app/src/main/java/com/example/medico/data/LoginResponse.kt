package com.example.medico.data

data class LoginResponse(
    val token: String,
    val firstName: String,
    val lastName: String,
    val age: String,
    val gender: String,
    val bloodGroup: String,
    val phone: String,
    val email: String,
)
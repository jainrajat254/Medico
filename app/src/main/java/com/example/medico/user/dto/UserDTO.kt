package com.example.medico.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "0",  // Default value
    val gender: String = "",
    val height: String = "0",  // Default value
    val weight: String = "0",  // Default value
    val bloodGroup: String = "",
    val email: String = "",
    val phone: String = ""
)


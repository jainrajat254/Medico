package com.example.medico.data

import kotlinx.serialization.Serializable

@Serializable
data class EditUserDTO(
    val age: String?,
    val bloodGroup: String?,
    val phone: String?,
    val email: String?
)


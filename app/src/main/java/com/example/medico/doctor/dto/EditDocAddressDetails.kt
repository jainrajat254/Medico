package com.example.medico.doctor.dto

data class EditDocAddressDetails(
    val workspaceName: String,
    val address: String,
    val state: String,
    val district: String,
    val zipCode: String,
)

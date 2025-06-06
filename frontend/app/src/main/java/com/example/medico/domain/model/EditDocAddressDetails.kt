package com.example.medico.domain.model

data class EditDocAddressDetails(
    val workspaceName: String,
    val address: String,
    val state: String,
    val district: String,
    val zipCode: String,
)

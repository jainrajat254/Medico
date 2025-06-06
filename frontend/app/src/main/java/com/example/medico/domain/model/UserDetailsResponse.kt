package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsResponse(
    val id: String = "",
    val weight: String = "",
    val height: String = "",
    val dob: String = "",
    val emergencyContactName: String = "",
    val emergencyContactRelation: String = "",
    val emergencyContactPhone: String = "",
    val state: String = "",
    val district: String = "",
    val city: String = "",
    val currentAddress: String = "",
    val permanentAddress: String = "",
    val knownHealthConditions: String = "",
    val familyMedicalHistory: String = "",
    val insuranceProvider: String = "",
    val policyNumber: String = "",
    val groupNumber: String = "",
    val coverageDetails: String = ""
)

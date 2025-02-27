package com.example.medico.user.responses

import kotlinx.serialization.Serializable

@Serializable
data class RecordsResponse(
    val review: String,
    val id: String,
    val record: ByteArray?,
    val recordName: String,
    val reviewedBy: String,
    val date: String
)
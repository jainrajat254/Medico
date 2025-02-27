package com.example.medico.user.model

import kotlinx.serialization.Serializable

@Serializable
data class Records(
    val recordName: String,
    val reviewedBy: String,
    val review: String,
    val recordFile: ByteArray
)

package com.example.medico.user.model

import kotlinx.serialization.Serializable

@Serializable
data class Reports(
    val reportName: String,
    val reviewedBy: String,
    val attentionLevel: String,
    val reportFile: ByteArray
)

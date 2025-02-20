package com.example.medico.user.responses

import kotlinx.serialization.Serializable

@Serializable
data class ReportsResponse(
    val attentionLevel: String,
    val id: String,
    val report: ByteArray?,
    val reportName: String,
    val reviewedBy: String
)
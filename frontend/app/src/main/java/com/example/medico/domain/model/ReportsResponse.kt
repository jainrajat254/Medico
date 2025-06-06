package com.example.medico.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReportsResponse(
    val attentionLevel: String,
    val id: String,
    val report: ByteArray?,
    val reportName: String,
    val reviewedBy: String,
    val date: String
)
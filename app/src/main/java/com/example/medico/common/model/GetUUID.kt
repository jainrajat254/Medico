package com.example.medico.common.model

import kotlinx.serialization.Serializable

@Serializable
data class GetUUID(
    val personal_info_uuid: String
)

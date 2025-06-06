package com.example.medico.domain.repository

import com.example.medico.domain.model.Records
import com.example.medico.domain.model.RecordsResponse

interface RecordsRepository {

    suspend fun addRecords(record: Records, userId: String): Records

    suspend fun getRecords(id: String): List<RecordsResponse>

    suspend fun getRecordFile(recordId: String): ByteArray
}
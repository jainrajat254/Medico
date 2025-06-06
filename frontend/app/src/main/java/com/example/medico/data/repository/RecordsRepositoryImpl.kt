package com.example.medico.data.repository

import com.example.medico.domain.model.Records
import com.example.medico.domain.model.RecordsResponse
import com.example.medico.domain.repository.RecordsRepository
import com.example.medico.domain.service.ApiService

class RecordsRepositoryImpl(private val apiService: ApiService) : RecordsRepository {

    override suspend fun addRecords(record: Records, userId: String): Records {
        return apiService.addRecords(record, userId)
    }

    override suspend fun getRecords(id: String): List<RecordsResponse> {
        return apiService.getRecords(id)
    }

    override suspend fun getRecordFile(recordId: String): ByteArray {
        return apiService.getRecordFile(recordId)
    }
}
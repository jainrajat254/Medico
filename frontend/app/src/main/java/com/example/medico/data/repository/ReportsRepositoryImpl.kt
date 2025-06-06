package com.example.medico.data.repository

import com.example.medico.domain.model.Reports
import com.example.medico.domain.model.ReportsResponse
import com.example.medico.domain.repository.ReportsRepository
import com.example.medico.domain.service.ApiService

class ReportsRepositoryImpl(private val apiService: ApiService) : ReportsRepository {

    override suspend fun addReports(request: Reports, id: String): Reports {
        return apiService.addReports(request, id)
    }

    override suspend fun getReports(id: String): List<ReportsResponse> {
        return apiService.getReports(id)
    }

    override suspend fun getReportFile(reportId: String): ByteArray {
        return apiService.getReportFile(reportId)
    }
}
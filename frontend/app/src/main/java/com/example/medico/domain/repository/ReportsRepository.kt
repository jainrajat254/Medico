package com.example.medico.domain.repository

import com.example.medico.domain.model.Reports
import com.example.medico.domain.model.ReportsResponse

interface ReportsRepository {

    suspend fun addReports(request: Reports, id: String): Reports

    suspend fun getReports(id: String): List<ReportsResponse>

    suspend fun getReportFile(reportId: String): ByteArray

}
package com.example.medico.presentation.viewmodel

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.domain.model.Reports
import com.example.medico.domain.model.ReportsResponse
import com.example.medico.domain.repository.ReportsRepository
import com.example.medico.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ReportsViewModel(
    private val reportsRepository: ReportsRepository,
) : ViewModel() {

    private val _addReportState = MutableStateFlow<ResultState<Reports>>(ResultState.Idle)
    val addReportState: StateFlow<ResultState<Reports>> = _addReportState

    private val _getReportsState =
        MutableStateFlow<ResultState<List<ReportsResponse>>>(ResultState.Idle)
    val getReportsState: StateFlow<ResultState<List<ReportsResponse>>> = _getReportsState

    fun loadReportsForCurrentUser(userId: String) {
        if (_getReportsState.value is ResultState.Success) return
        if (userId.isNotBlank()) {
            getReports(userId)
            Log.d("ReportsViewModel", "loadReportsForCurrentUser: fetching reports for user $userId")
        } else {
            Log.d("ReportsViewModel", "loadReportsForCurrentUser: userId is blank, skipping fetch")
        }
    }

    private fun <T> launchWithResult(
        state: MutableStateFlow<ResultState<T>>,
        block: suspend () -> T,
    ) {
        viewModelScope.launch {
            state.value = ResultState.Loading
            try {
                val result = withContext(Dispatchers.IO) {
                    block()
                }
                state.value = ResultState.Success(result)
            } catch (e: Exception) {
                state.value = ResultState.Error(e)
            }
        }
    }

    fun addReport(report: Reports, userId: String) {
        launchWithResult(_addReportState) {
            reportsRepository.addReports(report, userId)
        }
    }

    fun getReports(id: String) {
        launchWithResult(_getReportsState) {
            reportsRepository.getReports(id)
        }
    }

    fun downloadAndOpenPdf(reportId: String, context: Context) {
        viewModelScope.launch {
            try {
                val pdfData =
                    reportsRepository.getReportFile(reportId) // ✅ Go through Repository layer
                val file = File(context.getExternalFilesDir(null), "report_$reportId.pdf")
                file.writeBytes(pdfData)

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/pdf")
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }

                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "No PDF viewer found!", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("PDF", "Error opening PDF", e)
            }
        }
    }

    fun exportPdf(reportId: String, context: Context) {
        viewModelScope.launch {
            try {
                val pdfData = reportsRepository.getReportFile(reportId) // ✅ Use repo
                val downloadsDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, "Report_$reportId.pdf")
                file.writeBytes(pdfData)

                Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_SHORT).show()
                sharePdf(file, context)
            } catch (e: Exception) {
                Log.e("ExportPDF", "Error exporting PDF", e)
                Toast.makeText(context, "Failed to export PDF", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sharePdf(file: File, context: Context) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share PDF via"))
    }
}

package com.example.medico.user.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.common.koin.ApiService
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.user.model.Reports
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReportsViewModel(
    private val apiService: ApiService,
    sharedPreferencesManager: SharedPreferencesManager,
) : ViewModel() {

    private val doc = sharedPreferencesManager.getDocFromSharedPreferences()
    private val _reportsState = MutableStateFlow(
        Reports("", "${doc?.firstName} ${doc?.lastName}", "", ByteArray(0))
    )
    val reportsState: StateFlow<Reports> = _reportsState

    fun addReports(id: String, data: Reports) {
        viewModelScope.launch {
            try {
                // ✅ Log values to check for missing fields
                Log.d("AddReports", "reportName: ${data.reportName}")
                Log.d("AddReports", "reviewedBy: ${data.reviewedBy}")
                Log.d("AddReports", "attentionLevel: ${data.attentionLevel}")
                Log.d("AddReports", "reportFile size: ${data.reportFile.size} bytes")

                // ✅ Ensure file is not empty before sending request
                if (data.reportFile.isEmpty()) {
                    Log.e("AddReports", "Error: reportFile is empty!")
                    return@launch
                }

                val result: Result<Reports> = apiService.addReports(data, id)
                Log.d("AddReports", "Success: Report added successfully")

            } catch (e: Exception) {
                Log.e("AddReports", "Unexpected error: ${e.message}")
            }
        }
    }
}

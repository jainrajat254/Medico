package com.example.medico.user.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.common.koin.ApiService
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.user.model.Records
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecordsViewModel(
    private val apiService: ApiService,
    sharedPreferencesManager: SharedPreferencesManager,
) : ViewModel() {

    private val doc = sharedPreferencesManager.getDocFromSharedPreferences()
    private val _recordsState = MutableStateFlow(
        Records("", "${doc?.firstName} ${doc?.lastName}", "${doc?.id}", ByteArray(0))
    )
    val recordsState: StateFlow<Records> = _recordsState

    fun addRecords(userId: String, record: Records) {
        viewModelScope.launch {
            try {
                Log.d("AddRecords", "Record Name: ${record.recordName}")
                Log.d("AddRecords", "Reviewed By: ${record.reviewedBy}")
                Log.d("AddRecords", "Review Status: ${record.review}")
                Log.d("AddRecords", "File Size: ${record.recordFile.size} bytes")

                if (record.recordFile.isEmpty()) {
                    Log.e("AddReports", "Error: recordFile is empty!")
                    return@launch
                }

                val result = apiService.addRecords(record, userId)
                Log.d("AddReports", "Success: Report added successfully")

            } catch (e: Exception) {
                Log.e("AddReports", "Unexpected error: ${e.message}")
            }
        }
    }
}

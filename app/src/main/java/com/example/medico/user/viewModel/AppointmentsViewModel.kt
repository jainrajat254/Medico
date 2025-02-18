package com.example.medico.user.viewModel

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.common.koin.ApiService
import com.example.medico.doctor.dto.DoctorDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentsViewModel(private val apiService: ApiService) : ViewModel() {
    private val _doctors = MutableStateFlow<List<DoctorDTO>>(emptyList())
    val filteredDoctors: StateFlow<List<DoctorDTO>> = _doctors

    // Search query state
    private val _searchQuery = MutableStateFlow(TextFieldValue(""))
    val searchQuery: StateFlow<TextFieldValue> = _searchQuery

    var selectedState: String = ""
    var selectedDistrict: String = ""

    init {
        fetchDoctors()
    }

    // Fetch doctors from API
    private fun fetchDoctors() {
        viewModelScope.launch {
            try {
                val result = apiService.getDoctors() // Assuming apiService is initialized
                result.onSuccess { response ->
                    _doctors.value = response
                    applyFilters() // Apply filters whenever data is fetched
                }.onFailure { error ->
                    Log.e("AppointmentsViewModel", "Error fetching doctors", error)
                }
            } catch (e: Exception) {
                Log.e("AppointmentsViewModel", "Error fetching doctors", e)
            }
        }
    }

    fun updateSearchQuery(newQuery: TextFieldValue) {
        _searchQuery.value = newQuery
        applyFilters() // Filter doctors on query change
    }

    // Apply filters to doctors list
    private fun applyFilters() {
        _doctors.value = _doctors.value.filter {
            // Filter based on search query and selected filters
            (searchQuery.value.text.isEmpty() ||
                    "${it.firstName} ${it.lastName}".contains(searchQuery.value.text, true) ||
                    it.workspaceName.contains(searchQuery.value.text, true) ||
                    it.state.contains(searchQuery.value.text, true) ||
                    it.zipCode.contains(searchQuery.value.text, true) ||
                    it.specialization.contains(searchQuery.value.text, true)) &&
                    (selectedState.isEmpty() || it.state.equals(selectedState, true)) &&
                    (selectedDistrict.isEmpty() || it.district.equals(selectedDistrict, true))
        }
    }

    fun updateState(newState: String) {
        selectedState = newState
        applyFilters() // Apply filters after state update
    }

    // Update district filter
    fun updateDistrict(newDistrict: String) {
        selectedDistrict = newDistrict
        applyFilters() // Apply filters after district update
    }
}
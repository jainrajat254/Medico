package com.example.medico.doctor.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.common.koin.ApiService
import com.example.medico.user.model.Medications
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddMedications(private val apiService: ApiService) : ViewModel() {

    private val _medicationState = MutableStateFlow(Medications("", "", "", "", "", "", "", ""))
    val medicationState: StateFlow<Medications> = _medicationState

    fun addMedications(
        id: String,
        onSuccess: (Medications) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result: Result<Medications> =
                    apiService.addMedications(_medicationState.value, id)

                result.onSuccess { response ->
                    Log.d("AddMedications", "Success: $response")
                    onSuccess(response)
                }.onFailure { exception ->
                    Log.e("AddMedications", "Error: ${exception.message}")
                    onError("Error during adding medication: ${exception.message}")
                }

            } catch (e: Exception) {
                Log.e("AddMedications", "Unexpected error: ${e.message}")
                onError("Unexpected error: ${e.message}")
            }
        }
    }

    fun updateMedicationState(
        name: String? = null,
        dosageType: String? = null,
        dosage: String? = null,
        time: String? = null,
        frequency: String? = null,
        duration: String? = null,
        intakeMethod: String? = null,
        medicationType: String? = null,

        ) {
        _medicationState.value = _medicationState.value.copy(
            medicationName = name ?: _medicationState.value.medicationName,
            medicationType = medicationType ?: _medicationState.value.medicationType,
            dosage = dosage ?: _medicationState.value.dosage,
            time = time ?: _medicationState.value.time,
            frequency = frequency ?: _medicationState.value.frequency,
            duration = duration ?: _medicationState.value.duration,
            intakeMethod = intakeMethod ?: _medicationState.value.intakeMethod,
            dosageType = dosageType ?: _medicationState.value.dosageType
        )
    }
}


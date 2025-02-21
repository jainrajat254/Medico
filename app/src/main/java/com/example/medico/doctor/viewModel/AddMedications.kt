package com.example.medico.doctor.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.common.koin.ApiService
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.user.model.Medications
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddMedications(
    private val apiService: ApiService,
    sharedPreferencesManager: SharedPreferencesManager,
) : ViewModel() {

    private val _medicationState = MutableStateFlow(
        Medications(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            sharedPreferencesManager.getDocId()
        )
    )
    val medicationState: StateFlow<Medications> = _medicationState

    fun setMedicationForEdit(medication: Medications) {
        _medicationState.value = medication
    }

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

    fun updateMedication(
        medId: String,
        onSuccess: (Medications) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result: Result<Medications> =
                    apiService.updateMedication(medId, _medicationState.value)

                result.onSuccess { response ->
                    Log.d("UpdateMedication", "Success: $response")
                    onSuccess(response)
                }.onFailure { exception ->
                    Log.e("UpdateMedication", "Error: ${exception.message}")
                    onError("Error during updating medication: ${exception.message}")
                }
            } catch (e: Exception) {
                Log.e("UpdateMedication", "Unexpected error: ${e.message}")
                onError("Unexpected error: ${e.message}")
            }
        }
    }

    fun updateMedicationState(
        medicationName: String = _medicationState.value.medicationName,
        medicationType: String = _medicationState.value.medicationType,
        dosageType: String = _medicationState.value.dosageType,
        intakeMethod: String = _medicationState.value.intakeMethod,
        frequency: String = _medicationState.value.frequency,
        duration: String = _medicationState.value.duration,
        time: String = _medicationState.value.time
    ) {
        _medicationState.value = _medicationState.value.copy(
            medicationName = medicationName,
            medicationType = medicationType,
            dosageType = dosageType,
            intakeMethod = intakeMethod,
            frequency = frequency,
            duration = duration,
            time = time
        )
    }

}

package com.example.medico.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.domain.model.MedicationResponse
import com.example.medico.domain.model.Medications
import com.example.medico.domain.model.MedicationsDTO
import com.example.medico.domain.model.OldMedicationsDTO
import com.example.medico.domain.repository.MedicationsRepository
import com.example.medico.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MedicationsViewModel(
    private val medicationsRepository: MedicationsRepository,
) : ViewModel() {

    private val _addMedicationState = MutableStateFlow<ResultState<Medications>>(ResultState.Idle)
    val addMedicationState: StateFlow<ResultState<Medications>> = _addMedicationState

    private val _getMedicationsState =
        MutableStateFlow<ResultState<List<MedicationResponse>>>(ResultState.Idle)
    val getMedicationsState: StateFlow<ResultState<List<MedicationResponse>>> = _getMedicationsState

    private val _doctorMedicationsState =
        MutableStateFlow<ResultState<List<MedicationsDTO>>>(ResultState.Idle)
    val doctorMedicationsState: StateFlow<ResultState<List<MedicationsDTO>>> =
        _doctorMedicationsState

    private val _updateMedicationState =
        MutableStateFlow<ResultState<Medications>>(ResultState.Idle)
    val updateMedicationState: StateFlow<ResultState<Medications>> = _updateMedicationState

    private val _removeMedicationState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val removeMedicationState: StateFlow<ResultState<String>> = _removeMedicationState

    private val _oldMedicationsState =
        MutableStateFlow<ResultState<List<OldMedicationsDTO>>>(ResultState.Idle)
    val oldMedicationsState: StateFlow<ResultState<List<OldMedicationsDTO>>> = _oldMedicationsState

    fun loadAllMedicationsForCurrentUser(userId: String) {
        if (_oldMedicationsState.value is ResultState.Success && _getMedicationsState.value is ResultState.Success) return
        oldMedications(userId)
        Log.d("OLD MEDICATIONS", "CALLING OLD MEDICATIONS")
        getMedications(userId)
        Log.d("CURRENT MEDICATIONS", "CALLING CURRENT MEDICATIONS ")
    }

    fun loadCurrentMedicationsForCurrentUser(userId: String) {
        if (_getMedicationsState.value is ResultState.Success) return
        getMedications(userId)
        Log.d("CURRENT MEDICATIONS", "CALLING CURRENT MEDICATIONS ")
    }

    fun loadOldMedicationsForCurrentUser(userId: String) {
        if (_oldMedicationsState.value is ResultState.Success) return
        oldMedications(userId)
        Log.d("OLD MEDICATIONS", "CALLING OLD MEDICATIONS ")
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

    fun addMedications(request: Medications, id: String) {
        launchWithResult(_addMedicationState) {
            medicationsRepository.addMedications(request, id)
        }
    }

    fun getMedications(id: String) {
        launchWithResult(_getMedicationsState) {
            medicationsRepository.getMedications(id)
        }
    }

    fun doctorMedication(doctorId: String, userId: String) {
        launchWithResult(_doctorMedicationsState) {
            medicationsRepository.doctorMedication(doctorId, userId)
        }
    }

    fun updateMedication(medId: String, data: Medications) {
        launchWithResult(_updateMedicationState) {
            medicationsRepository.updateMedication(medId, data)
        }
    }

    fun removeMedications(medId: String) {
        launchWithResult(_removeMedicationState) {
            medicationsRepository.removeMedications(medId)
        }
    }

    fun oldMedications(userId: String) {
        launchWithResult(_oldMedicationsState) {
            medicationsRepository.oldMedications(userId)
        }
    }
}

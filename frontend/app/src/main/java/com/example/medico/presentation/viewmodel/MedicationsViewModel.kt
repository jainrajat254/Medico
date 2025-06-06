package com.example.medico.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.domain.model.MedicationResponse
import com.example.medico.domain.model.Medications
import com.example.medico.domain.model.MedicationsDTO
import com.example.medico.domain.model.OldMedicationsDTO
import com.example.medico.domain.repository.MedicationsRepository
import com.example.medico.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    private fun <T> launchWithResult(
        state: MutableStateFlow<ResultState<T>>,
        block: suspend () -> T,
    ) {
        viewModelScope.launch {
            state.value = ResultState.Loading
            state.value = try {
                ResultState.Success(block())
            } catch (e: Exception) {
                ResultState.Error(e)
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

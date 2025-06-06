package com.example.medico.presentation.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.domain.model.*
import com.example.medico.domain.repository.SettingsRepository
import com.example.medico.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _editUserDetailsState = MutableStateFlow<ResultState<UserDetails>>(ResultState.Idle)
    val editUserDetailsState: StateFlow<ResultState<UserDetails>> = _editUserDetailsState

    private val _editDocPersonalState =
        MutableStateFlow<ResultState<DoctorLoginResponse>>(ResultState.Idle)
    val editDocPersonalState: StateFlow<ResultState<DoctorLoginResponse>> = _editDocPersonalState

    private val _editPasswordState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val editPasswordState: StateFlow<ResultState<String>> = _editPasswordState

    private val _editDocAddressState =
        MutableStateFlow<ResultState<DoctorLoginResponse>>(ResultState.Idle)
    val editDocAddressState: StateFlow<ResultState<DoctorLoginResponse>> = _editDocAddressState

    private val _editDocMedicalState =
        MutableStateFlow<ResultState<DoctorLoginResponse>>(ResultState.Idle)
    val editDocMedicalState: StateFlow<ResultState<DoctorLoginResponse>> = _editDocMedicalState

    private val _addExtraDetailsState =
        MutableStateFlow<ResultState<UserDetailsResponse>>(ResultState.Idle)
    val addExtraDetailsState: StateFlow<ResultState<UserDetailsResponse>> = _addExtraDetailsState

    private val _getExtraDetailsState =
        MutableStateFlow<ResultState<UserDetailsResponse>>(ResultState.Idle)
    val getExtraDetailsState: StateFlow<ResultState<UserDetailsResponse>> = _getExtraDetailsState

    private val _getPersonalInfoIdState = MutableStateFlow<ResultState<GetExtraDetailsId>>(ResultState.Idle)
    val getPersonalInfoIdState: StateFlow<ResultState<GetExtraDetailsId>> = _getPersonalInfoIdState

    private val _getDoctorsState = MutableStateFlow<ResultState<List<DoctorDTO>>>(ResultState.Idle)
    val getDoctorsState: StateFlow<ResultState<List<DoctorDTO>>> = _getDoctorsState

    private val _getDetailsState = MutableStateFlow<ResultState<UserDTO>>(ResultState.Idle)
    val getDetailsState: StateFlow<ResultState<UserDTO>> = _getDetailsState

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

    fun editDetails(data: EditUserPersonalDetails, id: String) {
        launchWithResult(_editUserDetailsState) {
            settingsRepository.editDetails(data, id)
        }
    }

    fun editDocPersonalDetails(data: EditDocPersonalDetails, id: String) {
        launchWithResult(_editDocPersonalState) {
            settingsRepository.editDocPersonalDetails(data, id)
        }
    }

    fun editPassword(data: EditPassword, id: String) {
        launchWithResult(_editPasswordState) {
            settingsRepository.editPassword(data, id)
        }
    }

    fun editDocAddressDetails(data: EditDocAddressDetails, id: String) {
        launchWithResult(_editDocAddressState) {
            settingsRepository.editDocAddressDetails(data, id)
        }
    }

    fun editDocMedicalDetails(data: EditDocMedicalDetails, id: String) {
        launchWithResult(_editDocMedicalState) {
            settingsRepository.editDocMedicalDetails(data, id)
        }
    }

    fun addExtraDetails(data: ExtraDetails, id: String) {
        launchWithResult(_addExtraDetailsState) {
            settingsRepository.addExtraDetails(data, id)
        }
    }

    fun getExtraDetails(id: String) {
        launchWithResult(_getExtraDetailsState) {
            settingsRepository.getExtraDetails(id)
        }
    }

    fun getPersonalInfoId(id: String) {
        launchWithResult(_getPersonalInfoIdState) {
            settingsRepository.getPersonalInfoId(id)
        }
    }

    fun getDoctors() {
        launchWithResult(_getDoctorsState) {
            settingsRepository.getDoctors()
        }
    }

    fun getDetails(id: String) {
        launchWithResult(_getDetailsState) {
            settingsRepository.getDetails(id)
        }
    }

    private val _allDoctors = MutableStateFlow<List<DoctorDTO>>(emptyList())

    // Filtered list shown in UI
    private val _filteredDoctors = MutableStateFlow<List<DoctorDTO>>(emptyList())
    val filteredDoctors: StateFlow<List<DoctorDTO>> = _filteredDoctors

    // Search query state
    private val _searchQuery = MutableStateFlow(TextFieldValue(""))
    val searchQuery: StateFlow<TextFieldValue> = _searchQuery

    fun updateSearchQuery(query: TextFieldValue) {
        _searchQuery.value = query
        applyFilters()
    }

    // Filter criteria
    var selectedState: String = ""
        private set

    var selectedDistrict: String = ""
        private set

    fun updateState(state: String) {
        selectedState = state
        applyFilters()
    }

    fun updateDistrict(district: String) {
        selectedDistrict = district
        applyFilters()
    }

    // Set full list after fetching from backend
    fun setAllDoctors(doctors: List<DoctorDTO>) {
        _allDoctors.value = doctors
        applyFilters()
    }

    // Local filter logic
    fun applyFilters() {
        val query = _searchQuery.value.text.trim().lowercase()

        _filteredDoctors.value = _allDoctors.value.filter { doctor ->
            val name = "${doctor.firstName} ${doctor.lastName}".lowercase()
            val specialization = doctor.specialization.lowercase()
            val state = doctor.state.lowercase()
            val district = doctor.district.lowercase()
            val workspace = doctor.workspaceName.lowercase()
            val zipCode = doctor.zipCode.lowercase()

            (query.isBlank() ||
                    name.contains(query) ||
                    specialization.contains(query) ||
                    workspace.contains(query) ||
                    zipCode.contains(query) ||
                    state.contains(query)) &&
                    (selectedState.isBlank() || state.equals(
                        selectedState.lowercase(),
                        ignoreCase = true
                    )) &&
                    (selectedDistrict.isBlank() || district.equals(
                        selectedDistrict.lowercase(),
                        ignoreCase = true
                    ))
        }
    }
}

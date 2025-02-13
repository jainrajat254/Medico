package com.example.medico.doctor.viewModel

import androidx.lifecycle.ViewModel
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DoctorRegister(private val sharedPreferencesManager: SharedPreferencesManager) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _uid = MutableStateFlow("")
    val uid: StateFlow<String> = _uid

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String> = _dob

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _workspaceName = MutableStateFlow("")
    val workspaceName: StateFlow<String> = _workspaceName

    private val _state = MutableStateFlow("")
    val state: StateFlow<String> = _state

    private val _district = MutableStateFlow("")
    val district: StateFlow<String> = _district

    private val _zipCode = MutableStateFlow("")
    val zipCode: StateFlow<String> = _zipCode

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address

    private val _medicalRegNo = MutableStateFlow("")
    val medicalRegNo: StateFlow<String> = _medicalRegNo

    private val _qualification = MutableStateFlow("")
    val qualification: StateFlow<String> = _qualification

    private val _specialization = MutableStateFlow("")
    val specialization: StateFlow<String> = _specialization

    private val _experience = MutableStateFlow(0)
    val experience: StateFlow<Int> = _experience

    private val _workingTime = MutableStateFlow<List<String>>(emptyList())
    val workingTime: StateFlow<List<String>> = _workingTime

    private val _fee = MutableStateFlow(0)
    val fee: StateFlow<Int> = _fee

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _availableForOnlineConsultation = MutableStateFlow(false)
    val availableForOnlineConsultation: StateFlow<Boolean> = _availableForOnlineConsultation

    fun updateFirstName(value: String) {
        _firstName.value = value
    }

    fun updateLastName(value: String) {
        _lastName.value = value
    }

    fun updateUid(value: String) {
        _uid.value = value
    }

    fun updateDob(value: String) {
        _dob.value = value
    }

    fun updateGender(gender: String) {
        _gender.value = gender
    }

    fun updateWorkspaceName(value: String) {
        _workspaceName.value = value
    }

    fun updateState(value: String) {
        _state.value = value
    }

    fun updateDistrict(value: String) {
        _district.value = value
    }

    fun updateZipCode(value: String) {
        _zipCode.value = value
    }

    fun updateAddress(value: String) {
        _address.value = value
    }

    fun updateMedicalRegNo(value: String) {
        _medicalRegNo.value = value
    }

    fun updateQualification(value: String) {
        _qualification.value = value
    }

    fun updateSpecialization(value: String) {
        _specialization.value = value
    }

    fun updateExperience(value: String) {
        _experience.value = value.toIntOrNull() ?: 0

    }

    fun updateWorkingTime(time: String) {
        _workingTime.value = if (_workingTime.value.contains(time)) {
            _workingTime.value.filterNot { it == time }
        } else {
            _workingTime.value + time
        }
    }

    fun updateFee(value: String) {
        _fee.value = value.toIntOrNull() ?: 0
    }

    fun updatePhone(value: String) {
        _phone.value = value
    }

    fun updateEmail(value: String) {

        _email.value = value

    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun updateAvailableForOnlineConsultation(value: String) {
        _availableForOnlineConsultation.value = value.toBoolean()
    }

    fun isFormValid(): String? {
        return when {
            firstName.value.isBlank() -> "First Name is required"
            lastName.value.isBlank() -> "Last Name is required"
            uid.value.length != 12 || !uid.value.all { it.isDigit() } -> "Aadhar Number must be 12 digits"
            dob.value.isBlank() -> "Date of Birth is required"
            gender.toString().isBlank() -> "Gender is required"
            workspaceName.value.isBlank() -> "Workspace Name is required"
            state.toString().isBlank() -> "State is required"
            district.value.isBlank() -> "District is required"
            zipCode.value.length != 6 || !zipCode.value.all { it.isDigit() } -> "Zip Code must be 6 digits"
            address.value.isBlank() -> "Address is required"
            medicalRegNo.value.isBlank() -> "Medical Registration Number is required"
            qualification.value.isBlank() -> "Qualification is required"
            specialization.value.isBlank() -> "Specialization is required"
            experience.value == 0 -> "Experience is required"
            phone.value.length != 10 || !phone.value.all { it.isDigit() } -> "Phone Number must be 10 digits"
            email.value.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value)
                .matches() -> "Invalid Email Address"

            password.value.isBlank() -> "Password is required"
            else -> null  // No error
        }
    }


}



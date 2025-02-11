package com.example.medico.models

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.medico.sharedPreferences.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DocAccountViewModel(private val sharedPreferencesManager: SharedPreferencesManager) : ViewModel() {

    private val user = sharedPreferencesManager.getDocFromSharedPreferences()

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing

    private val _id = MutableStateFlow(user?.id ?: "null")
    val id: StateFlow<String> = _id

    private val _name = MutableStateFlow((user?.firstName ?: "null") + " " + (user?.lastName ?: "null"))
    val name: StateFlow<String> = _name

    private val _dob = MutableStateFlow(user?.dob ?: "null")
    val dob: StateFlow<String> = _dob

    private val _uid = MutableStateFlow(user?.uid ?: "null")
    val uid: StateFlow<String> = _uid

    private val _gender = MutableStateFlow(user?.gender ?: "null")
    val gender: StateFlow<String> = _gender

    private val _phone = MutableStateFlow(user?.phone ?: "null")
    val phone: StateFlow<String> = _phone

    private val _email = MutableStateFlow(user?.email ?: "null")
    val email: StateFlow<String> = _email

    private val _address = MutableStateFlow(user?.address ?: "null")
    val address: StateFlow<String> = _address

    private val _availableForOnlineConsultation = MutableStateFlow(user?.availableForOnlineConsultation ?: false)
    val availableForOnlineConsultation: StateFlow<Boolean> = _availableForOnlineConsultation

    private val _state = MutableStateFlow(user?.state ?: "null")
    val state: StateFlow<String> = _state

    private val _district = MutableStateFlow(user?.district ?: "null")
    val district: StateFlow<String> = _district

    private val _zipCode = MutableStateFlow(user?.zipCode ?: "null")
    val zipCode: StateFlow<String> = _zipCode

    private val _qualification = MutableStateFlow(user?.qualification ?: "null")
    val qualification: StateFlow<String> = _qualification

    private val _specialization = MutableStateFlow(user?.specialization ?: "null")
    val specialization: StateFlow<String> = _specialization

    private val _medicalRegNo = MutableStateFlow(user?.medicalRegNo ?: "null")
    val medicalRegNo: StateFlow<String> = _medicalRegNo

    private val _workspaceName = MutableStateFlow(user?.workspaceName ?: "null")
    val workspaceName: StateFlow<String> = _workspaceName

    private val _workingTime = MutableStateFlow(user?.workingTime ?: listOf<String>())
    val workingTime: StateFlow<List<String>> = _workingTime

    private val _experience = MutableStateFlow(user?.experience ?: 0)
    val experience: StateFlow<Int> = _experience

    private val _fee = MutableStateFlow(user?.fee ?: 0)
    val fee: StateFlow<Int> = _fee

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    fun toggleEditing() {
        _isEditing.value = !_isEditing.value
    }

    fun updateUid(newUid: String) {
        if (newUid != _uid.value) _uid.value = newUid
    }

    fun updateDOB(newDOB: String) {
        if (newDOB != _dob.value) _dob.value = newDOB
    }

    fun updateAddress(newAddress: String) {
        if (newAddress != _address.value) _address.value = newAddress
    }

    fun updateAvailableForOnlineConsultation(newAvailableForOnlineConsultation: Boolean) {
        if (newAvailableForOnlineConsultation != _availableForOnlineConsultation.value) _availableForOnlineConsultation.value = newAvailableForOnlineConsultation
    }

    fun updateState(newState: String) {
        if (newState != _state.value) _state.value = newState
    }

    fun updateDistrict(newDistrict: String) {
        if (newDistrict != _district.value) _district.value = newDistrict
    }

    fun updateZipCode(newZipCode: String) {
        if (newZipCode != _zipCode.value) _zipCode.value = newZipCode
    }

    fun updateQualification(newQualification: String) {
        if (newQualification != _qualification.value) _qualification.value = newQualification
    }

    fun updateSpecialization(newSpecialization: String) {
        if (newSpecialization != _specialization.value) _specialization.value = newSpecialization
    }

    fun updateMedicalRegNo(newMedicalRegNo: String) {
        if (newMedicalRegNo != _medicalRegNo.value) _medicalRegNo.value = newMedicalRegNo
    }

    fun updateWorkspaceName(newWorkspaceName: String) {
        if (newWorkspaceName != _workspaceName.value) _workspaceName.value = newWorkspaceName
    }

    fun updateWorkingTime(newWorkingTime: List<String>) {
        if (newWorkingTime != _workingTime.value) _workingTime.value = newWorkingTime
    }

    fun updateExperience(newExperience: Int) {
        if (newExperience != _experience.value) _experience.value = newExperience
    }

    fun updateFee(newFee: Int) {
        if (newFee != _fee.value) _fee.value = newFee
    }

    fun updatePhone(newPhone: String) {
        if (newPhone != _phone.value) _phone.value = newPhone
    }

    fun updateEmail(newEmail: String) {
        if (newEmail != _email.value) _email.value = newEmail
    }

    fun updateSelectedImageUri(uri: Uri?) {
        if (uri != _selectedImageUri.value) _selectedImageUri.value = uri
    }

    val dobRegex = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")

    fun isPersonalDetailsFormValid(): String? {
        return when {


            dob.value.isBlank() || !dobRegex.matches(dob.value) ->
                "Invalid date format. Use DD/MM/YYYY"

            uid.value.toIntOrNull() == null || uid.value.toInt() != 12 ->
                "Enter a valid Aadhar Number"

            phone.value.length != 10 || !phone.value.all { it.isDigit() } ->
                "Phone Number must be 10 digits"

            email.value.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value)
                .matches() ->
                "Invalid Email Address"

            else -> null
        }
    }
}

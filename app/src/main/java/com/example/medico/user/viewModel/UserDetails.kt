package com.example.medico.user.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserDetails(sharedPreferencesManager: SharedPreferencesManager) : ViewModel() {

    private val user = sharedPreferencesManager.getUserFromSharedPreferences()
    private val details = sharedPreferencesManager.getUserDetails()

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing

    private val _id = MutableStateFlow(user?.id ?: "null")
    val id: StateFlow<String> = _id

    private val _name =
        MutableStateFlow((user?.firstName ?: "null") + " " + (user?.lastName ?: "null"))
    val name: StateFlow<String> = _name

    private val _age = MutableStateFlow(user?.age ?: "null")
    val age: StateFlow<String> = _age

    private val _gender = MutableStateFlow(user?.gender ?: "null")
    val gender: StateFlow<String> = _gender

    private val _bloodGroup = MutableStateFlow(user?.bloodGroup ?: "null")
    val bloodGroup: StateFlow<String> = _bloodGroup

    private val _phone = MutableStateFlow(user?.phone ?: "null")
    val phone: StateFlow<String> = _phone

    private val _email = MutableStateFlow(user?.email ?: "null")
    val email: StateFlow<String> = _email

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    private val _weight = MutableStateFlow(details.weight)
    val weight: StateFlow<String> = _weight

    private val _height = MutableStateFlow(details.height)
    val height: StateFlow<String> = _height

    private val _dob = MutableStateFlow(details.dob)
    val dob: StateFlow<String> = _dob

    private val _emergencyContactName = MutableStateFlow(details.emergencyContactName)
    val emergencyContactName: StateFlow<String> = _emergencyContactName

    private val _emergencyContactRelation =
        MutableStateFlow(details.emergencyContactRelation)
    val emergencyContactRelation: StateFlow<String> = _emergencyContactRelation

    private val _emergencyContactPhone = MutableStateFlow(details.emergencyContactPhone)
    val emergencyContactPhone: StateFlow<String> = _emergencyContactPhone

    private val _state = MutableStateFlow(details.state)
    val state: StateFlow<String> = _state

    private val _district = MutableStateFlow(details.district)
    val district: StateFlow<String> = _district

    private val _city = MutableStateFlow(details.city)
    val city: StateFlow<String> = _city

    private val _currentAddress = MutableStateFlow(details.currentAddress)
    val currentAddress: StateFlow<String> = _currentAddress

    private val _permanentAddress = MutableStateFlow(details.permanentAddress)
    val permanentAddress: StateFlow<String> = _permanentAddress

    private val _knownHealthConditions = MutableStateFlow(details.knownHealthConditions)
    val knownHealthConditions: StateFlow<String> = _knownHealthConditions

    private val _familyMedicalHistory = MutableStateFlow(details.familyMedicalHistory)
    val familyMedicalHistory: StateFlow<String> = _familyMedicalHistory

    private val _insuranceProvider = MutableStateFlow(details.insuranceProvider)
    val insuranceProvider: StateFlow<String> = _insuranceProvider

    private val _policyNumber = MutableStateFlow(details.policyNumber)
    val policyNumber: StateFlow<String> = _policyNumber

    private val _groupNumber = MutableStateFlow(details.groupNumber)
    val groupNumber: StateFlow<String> = _groupNumber

    private val _coverageDetails = MutableStateFlow(details.coverageDetails)
    val coverageDetails: StateFlow<String> = _coverageDetails

    fun toggleEditing() {
        _isEditing.value = !_isEditing.value
    }

    fun updateAge(newAge: String) {
        _age.value = newAge
    }

    fun updateBloodGroup(newBloodGroup: String) {
        _bloodGroup.value = newBloodGroup
    }

    fun updatePhone(newPhone: String) {
        _phone.value = newPhone
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updateWeight(newWeight: String) {
        _weight.value = newWeight
    }

    fun updateHeight(newHeight: String) {
        _height.value = newHeight
    }

    fun updateDob(newDob: String) {
        _dob.value = newDob
    }

    fun updateEmergencyContactName(newName: String) {
        _emergencyContactName.value = newName
    }

    fun updateEmergencyContactRelation(newRelation: String) {
        _emergencyContactRelation.value = newRelation
    }

    fun updateEmergencyContactPhone(newPhone: String) {
        _emergencyContactPhone.value = newPhone
    }

    fun updateState(newState: String) {
        _state.value = newState
    }

    fun updateDistrict(newDistrict: String) {
        _district.value = newDistrict
    }

    fun updateCity(newCity: String) {
        _city.value = newCity
    }

    fun updateCurrentAddress(newAddress: String) {
        _currentAddress.value = newAddress
    }

    fun updatePermanentAddress(newAddress: String) {
        _permanentAddress.value = newAddress
    }

    fun updateKnownHealthConditions(newConditions: String) {
        _knownHealthConditions.value = newConditions
    }

    fun updateFamilyMedicalHistory(newHistory: String) {
        _familyMedicalHistory.value = newHistory
    }

    fun updateInsuranceProvider(newProvider: String) {
        _insuranceProvider.value = newProvider
    }

    fun updatePolicyNumber(newPolicy: String) {
        _policyNumber.value = newPolicy
    }

    fun updateGroupNumber(newGroup: String) {
        _groupNumber.value = newGroup
    }

    fun updateCoverageDetails(newCoverage: String) {
        _coverageDetails.value = newCoverage
    }

    fun updateSelectedImageUri(uri: Uri?) {
        if (uri != _selectedImageUri.value) _selectedImageUri.value = uri
    }

    fun isFormValid(): String? {
        return when {
            age.value.toIntOrNull() == null || age.value.toInt() < 0 || age.value.toInt() > 150 ->
                "Enter a valid age"

            phone.value.length != 10 || !phone.value.all { it.isDigit() } ->
                "Phone Number must be 10 digits"

            email.value.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value)
                .matches() ->
                "Invalid Email Address"

            else -> null
        }
    }
}

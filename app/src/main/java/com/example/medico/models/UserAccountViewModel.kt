package com.example.medico.models

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.medico.sharedPreferences.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserAccountViewModel(private val sharedPreferencesManager: SharedPreferencesManager) : ViewModel() {

    private val user = sharedPreferencesManager.getUserFromSharedPreferences()

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing

    private val _id = MutableStateFlow(user?.id ?: "null")
    val id: StateFlow<String> = _id

    private val _name = MutableStateFlow((user?.firstName ?: "null") + " " + (user?.lastName ?: "null"))
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

    fun toggleEditing() {
        _isEditing.value = !_isEditing.value
    }

    fun updateAge(newAge: String) {
        if (newAge != _age.value) _age.value = newAge
    }

    fun updateBloodGroup(newBloodGroup: String) {
        if (newBloodGroup != _bloodGroup.value) _bloodGroup.value = newBloodGroup
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

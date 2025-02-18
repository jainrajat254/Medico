package com.example.medico.common.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.medico.doctor.dto.EditDocAddressDetails
import com.example.medico.doctor.dto.EditDocMedicalDetails
import com.example.medico.doctor.model.DoctorDetails
import com.example.medico.doctor.responses.DoctorLoginResponse
import com.example.medico.doctor.dto.EditDocPersonalDetails
import com.example.medico.user.dto.EditUserPersonalDetails
import com.example.medico.common.model.LoginCredentials
import com.example.medico.user.responses.UserLoginResponse
import com.example.medico.common.dto.EditPassword
import com.example.medico.user.model.UserDetails
import com.example.medico.common.koin.ApiService
import com.example.medico.user.dto.UserDTO
import com.example.medico.user.model.ExtraDetails
import com.example.medico.user.responses.UserDetailsResponse
import kotlinx.coroutines.launch

class AuthViewModel(private val apiService: ApiService) : ViewModel() {

    fun registerUser(
        user: UserDetails,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                apiService.registerUser(user)
                onSuccess()
            } catch (e: HttpException) {
                Log.d("ViewModel", e.toString())
                onError("HTTP Error during registration: ${e.message}")
            } catch (e: Exception) {
                Log.d("ViewModel", e.toString())
                onError("Unexpected error during registration: ${e.message}")
            }
        }
    }

    fun loginDoc(
        user: LoginCredentials,
        onSuccess: (DoctorLoginResponse) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val userResponse = apiService.loginDoc(user)
                onSuccess(userResponse)
            } catch (e: HttpException) {
                Log.d("ViewModel", e.toString())
                onError("HTTP Error during login: ${e.message}")
            } catch (e: Exception) {
                Log.d("ViewModel", e.toString())
                onError("Unexpected error during login: ${e.message}")
            }
        }
    }

    fun registerDoc(
        doctorDetails: DoctorDetails,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                apiService.registerUser(doctorDetails)
                onSuccess()
            } catch (e: HttpException) {
                Log.d("ViewModel", e.toString())
                onError("HTTP Error during registration: ${e.message}")
            } catch (e: Exception) {
                Log.d("ViewModel", e.toString())
                onError("Unexpected error during registration: ${e.message}")
            }
        }
    }

    fun editDetails(
        data: EditUserPersonalDetails,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<UserDetails> = apiService.editDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun editDocPersonalDetails(
        data: EditDocPersonalDetails,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<DoctorLoginResponse> =
                    apiService.editDocPersonalDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun editPassword(
        data: EditPassword,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<UserDetails> = apiService.editPassword(data, userId)
                response.onSuccess {
                    onSuccess()
                }.onFailure {
                    onError("Error during edit: ${it.message}")
                }
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun editDocAddressDetails(
        data: EditDocAddressDetails,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<DoctorLoginResponse> =
                    apiService.editDocAddressDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun editDocMedicalDetails(
        data: EditDocMedicalDetails,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<DoctorLoginResponse> =
                    apiService.editDocMedicalDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun addExtraDetails(
        data: ExtraDetails,
        userId: String,
        onSuccess: (UserDetailsResponse) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result: Result<UserDetailsResponse> = apiService.addExtraDetails(data, userId)

                result.onSuccess { response ->
                    Log.d("AddExtraDetails", "Success: $response")
                    onSuccess(response)
                }.onFailure { exception ->
                    Log.e("AddExtraDetails", "Error: ${exception.message}")
                    onError("Error during edit: ${exception.message}")
                }

            } catch (e: Exception) {
                Log.e("AddExtraDetails", "Unexpected error: ${e.message}")
                onError("Unexpected error: ${e.message}")
            }
        }
    }

    fun getPersonalInfoId(id: String, onResult: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            apiService.getPersonalInfoId(id)
                .onSuccess { uuid -> onResult(uuid) }
                .onFailure { exception -> onError(exception.message ?: "Unknown error") }
        }
    }

    fun login(
        user: LoginCredentials,
        onSuccess: (UserLoginResponse) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result: Result<UserLoginResponse> = apiService.login(user)
                result.onSuccess { response ->
                    Log.d("Login", "Success: $response")
                    onSuccess(response)
                }.onFailure { exception ->
                    Log.e("AddExtraDetails", "Error: ${exception.message}")
                    onError("Error during edit: ${exception.message}")
                }
            } catch (e: Exception) {
                Log.d("ViewModel", e.toString())
                onError("Unexpected error during login: ${e.message}")
            }
        }
    }

    fun getExtraDetails(
        userId: String,
        onSuccess: (UserDetailsResponse) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result: Result<UserDetailsResponse> = apiService.getExtraDetails(userId)

                result.onSuccess { response ->
                    Log.d("GetExtraDetails", "Success: $response")
                    onSuccess(response)
                }.onFailure { exception ->
                    Log.e("GetExtraDetails", "Error: ${exception.message}")
                    onError("Error during edit: ${exception.message}")
                }

            } catch (e: Exception) {
                Log.e("AddExtraDetails", "Unexpected error: ${e.message}")
                onError("Unexpected error: ${e.message}")
            }
        }
    }

    fun getDetails(id: String,onResult: (UserDTO) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.getDetails(id)

                Log.d("UserOverview", "Raw API Response: $response") // Print API response

            } catch (e: Exception) {
                Log.e("UserOverview", "Exception fetching user details", e)
            }
        }

    }

}

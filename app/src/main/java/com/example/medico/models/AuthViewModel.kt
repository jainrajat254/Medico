package com.example.medico.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.network.HttpException
import com.example.medico.data.DoctorRegister
import com.example.medico.data.DoctorResponse
import com.example.medico.data.EditDocDTO
import com.example.medico.data.EditUserDTO
import com.example.medico.data.LoginCredentials
import com.example.medico.data.LoginResponse
import com.example.medico.data.UserData
import com.example.medico.koin.ApiService
import kotlinx.coroutines.launch

class AuthViewModel(private val apiService: ApiService) : ViewModel() {

    fun login(
        user: LoginCredentials,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val userResponse = apiService.login(user)
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

    fun register(
        user: UserData,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                apiService.registerDoc(user)
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
        onSuccess: (DoctorResponse) -> Unit,
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

    fun register(
        doctorRegister: DoctorRegister,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                apiService.registerDoc(doctorRegister)
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
        data: EditUserDTO,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<UserData> = apiService.editDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun editDocPersonalDetails(
        data: EditDocDTO,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<DoctorResponse> = apiService.editDocPersonalDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

}

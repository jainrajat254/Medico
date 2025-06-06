package com.example.medico.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.domain.model.DoctorDetails
import com.example.medico.domain.model.DoctorLoginResponse
import com.example.medico.domain.model.LoginCredentials
import com.example.medico.domain.model.UserDetails
import com.example.medico.domain.model.UserLoginResponse
import com.example.medico.domain.repository.AuthRepository
import com.example.medico.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _userLoginState =
        MutableStateFlow<ResultState<UserLoginResponse>>(ResultState.Idle)
    val userLoginState: StateFlow<ResultState<UserLoginResponse>> = _userLoginState

    private val _docLoginState =
        MutableStateFlow<ResultState<DoctorLoginResponse>>(ResultState.Idle)
    val docLoginState: StateFlow<ResultState<DoctorLoginResponse>> = _docLoginState

    private val _userRegisterState =
        MutableStateFlow<ResultState<UserDetails>>(ResultState.Idle)
    val userRegisterState: StateFlow<ResultState<UserDetails>> = _userRegisterState

    private val _docRegisterState =
        MutableStateFlow<ResultState<DoctorDetails>>(ResultState.Idle)
    val docRegisterState: StateFlow<ResultState<DoctorDetails>> = _docRegisterState

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

    fun loginUser(loginRequest: LoginCredentials) {
        launchWithResult(_userLoginState) {
            authRepository.login(loginRequest)
        }
    }

    fun registerUser(data: UserDetails) {
        launchWithResult(_userRegisterState) {
            authRepository.registerUser(data)
        }
    }

    fun loginDoc(user: LoginCredentials) {
        launchWithResult(_docLoginState) {
            authRepository.loginDoc(user)
        }
    }

    fun registerDoc(data: DoctorDetails) {
        launchWithResult(_docRegisterState) {
            authRepository.registerDoc(data)
        }
    }

    private val _state = MutableStateFlow("")
    val state: StateFlow<String> = _state

    private val _district = MutableStateFlow("")
    val district: StateFlow<String> = _district

    fun updateState(newState: String) {
        _state.value = newState
    }

    fun updateDistrict(newDistrict: String) {
        _district.value = newDistrict
    }

}
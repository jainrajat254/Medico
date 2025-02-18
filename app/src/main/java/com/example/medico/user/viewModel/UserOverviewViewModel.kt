package com.example.medico.user.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.common.koin.ApiService
import com.example.medico.user.dto.UserDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserOverviewViewModel(private val apiService: ApiService) : ViewModel() {

    private val _userDetails = MutableStateFlow<UserState>(UserState.Loading)
    val userDetails: StateFlow<UserState> = _userDetails

    fun fetchUserDetails(id: String) {
        viewModelScope.launch {
            try {
                val result = apiService.getDetails(id) // Returns Result<UserDTO>

                result.onSuccess { user ->
                    Log.d("UserOverviewViewModel", "✅ API Response: $user")
                    _userDetails.value = UserState.Success(user)
                }.onFailure { exception ->
                    Log.e("UserOverviewViewModel", "❌ API Error: ${exception.message}", exception)
                    _userDetails.value = UserState.Error("Failed to fetch user details")
                }
            } catch (e: Exception) {
                Log.e("UserOverviewViewModel", "❌ Unexpected Error: ${e.message}", e)
                _userDetails.value = UserState.Error("An unexpected error occurred")
            }
        }
    }

}

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: UserDTO) : UserState()
    data class Error(val message: String) : UserState()
}

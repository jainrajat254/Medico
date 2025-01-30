package com.example.medico.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medico.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")
    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> = _userData

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.UnAuthenticated
        } else {
            _authState.value = AuthState.Authenticated
            fetchUserData() // Fetch user data when authenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    fetchUserData() // Fetch user data on successful login
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    fetchUserData() // Fetch user data on successful signup
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
    }

    private fun fetchUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            usersRef.child(userId).get().addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(User::class.java)
                user?.let {
                    // Only update LiveData if user is non-null
                    _userData.value = it
                } ?: run {
                    // Handle the case when user data is null
                    _userData.value = User() // Set to a default empty User object
                    _authState.value = AuthState.Error("User data not found")
                }
            }.addOnFailureListener { e ->
                // Handle failure
                _authState.value = AuthState.Error("Error fetching user data: ${e.message}")
            }
        } else {
            _authState.value = AuthState.Error("User not authenticated")
        }
    }
}

sealed class AuthState {
    data object Authenticated : AuthState()
    data object UnAuthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

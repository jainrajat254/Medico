package com.example.medico.models

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.medico.data.User
import com.example.medico.data.BloodGroup
import com.example.medico.data.Gender
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterViewModel : ViewModel() {

    var firstName = mutableStateOf("")
    var lastName = mutableStateOf("")
    var age = mutableStateOf("")
    var weight = mutableStateOf("")
    var height = mutableStateOf("")
    var email = mutableStateOf("")
    var phone = mutableStateOf("")
    var gender = mutableStateOf<Gender?>(null)
    var bloodGroup = mutableStateOf<BloodGroup?>(null)
    var password = mutableStateOf("")
    var expanded = mutableStateOf(false)
    var bloodGroupExpanded = mutableStateOf(false)
    var isPasswordVisible = mutableStateOf(false)

    private var errors = mutableStateOf(mapOf<String, Boolean>())

    private fun isValidPhone(phone: String): Boolean {
        return phone.length == 10 && phone.all { it.isDigit() }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun isValidAge(age: String): Boolean {
        val ageInt = age.toIntOrNull()
        return ageInt != null && ageInt in 1..150
    }

    private fun isValidHeight(height: String): Boolean {
        val heightInt = height.toIntOrNull()
        return heightInt != null && heightInt in 30..300
    }

    private fun isValidWeight(weight: String): Boolean {
        val weightInt = weight.toIntOrNull()
        return weightInt != null && weightInt in 1..700
    }

    private fun validateForm(): Boolean {
        errors.value = mapOf(
            "firstName" to firstName.value.isBlank(),
            "lastName" to lastName.value.isBlank(),
            "age" to !isValidAge(age.value),
            "email" to !isValidEmail(email.value),
            "phone" to !isValidPhone(phone.value),
            "password" to !isValidPassword(password.value),
            "gender" to (gender.value == null),
            "bloodGroup" to (bloodGroup.value == null),
            "weight" to!isValidWeight(weight.value),
            "height" to!isValidHeight(height.value)
        )

        return errors.value.values.none { it }
    }

    // Method to handle registration click and show error messages
    private fun saveUserDataToDatabase(context: Context) {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val user = User(
            firstName = firstName.value,
            lastName = lastName.value,
            age = age.value,
            weight = weight.value,
            height = height.value,
            email = email.value,
            phone = phone.value,
            bloodGroup = bloodGroup.value?.group ?: "",
            gender = gender.value?.displayName ?: ""
        )

        // Save the User object
        usersRef.child(userId).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseDB", "Error saving data: ", e)
                Toast.makeText(context, "Error saving data.", Toast.LENGTH_SHORT).show()
            }
    }


    // Updated onRegisterClicked method
    fun onRegisterClicked(
        navController: NavHostController?,
        context: Context,
        authViewModel: AuthViewModel
    ) {
        if (validateForm()) {
            authViewModel.signup(email.value, password.value)

            authViewModel.authState.observeForever { authState ->
                when (authState) {
                    is AuthState.Authenticated -> {
                        // Save data to Firebase Realtime Database after authentication
                        saveUserDataToDatabase(context)

                        // Navigate to home screen
                        navController?.navigate("home")
                    }
                    is AuthState.Error -> {
                        Toast.makeText(context, authState.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // Handle other states if necessary
                    }
                }
            }
        } else {
            showValidationErrors(context)
        }
    }
    private fun showValidationErrors(context: Context) {
        if (errors.value["firstName"] == true && errors.value["lastName"] == true &&
            errors.value["age"] == true && errors.value["email"] == true &&
            errors.value["phone"] == true && errors.value["password"] == true &&
            errors.value["gender"] == true && errors.value["bloodGroup"] == true &&
            errors.value["weight"] == true && errors.value["height"] == true
        ) {
            Toast.makeText(
                context,
                "Please fill in all required fields correctly.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            when {
                errors.value["firstName"] == true -> {
                    Toast.makeText(context, "First Name is required.", Toast.LENGTH_SHORT).show()
                }
                errors.value["lastName"] == true -> {
                    Toast.makeText(context, "Last Name is required.", Toast.LENGTH_SHORT).show()
                }
                errors.value["age"] == true -> {
                    Toast.makeText(context, "Age is required.", Toast.LENGTH_SHORT).show()
                }
                errors.value["weight"] == true -> {
                    Toast.makeText(context, "Weight is required.", Toast.LENGTH_SHORT).show()
                }
                errors.value["height"] == true -> {
                    Toast.makeText(context, "Height is required.", Toast.LENGTH_SHORT).show()
                }
                errors.value["bloodGroup"] == true -> {
                    Toast.makeText(context, "Blood Group is required.", Toast.LENGTH_SHORT).show()
                }
                errors.value["gender"] == true -> {
                    Toast.makeText(context, "Gender is required.", Toast.LENGTH_SHORT).show()
                }
                errors.value["phone"] == true -> {
                    Toast.makeText(context, "Phone number must be 10 digits.", Toast.LENGTH_SHORT)
                        .show()
                }
                errors.value["email"] == true -> {
                    Toast.makeText(context, "Email is invalid.", Toast.LENGTH_SHORT).show()
                }
                errors.value["password"] == true -> {
                    Toast.makeText(
                        context,
                        "Password must be at least 6 characters.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

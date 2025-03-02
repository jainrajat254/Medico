package com.example.medico.common.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medico.common.dto.EditPassword
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.viewModel.AuthViewModel

@Composable
fun ChangePassword(
    sharedPreferencesManager: SharedPreferencesManager,
    vm: AuthViewModel,
    navController: NavController,
) {
    val role = sharedPreferencesManager.getUserRole()
    val id =
        if (role == "USER") sharedPreferencesManager.getUserId() else sharedPreferencesManager.getDocId()

    var password by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmNewPassword by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Password visibility states
    var passwordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmNewPasswordVisible by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    PasswordField(
                        label = "Current Password",
                        value = password,
                        onValueChange = { password = it },
                        isPasswordVisible = passwordVisible,
                        onVisibilityChange = { passwordVisible = !passwordVisible }
                    )
                    PasswordField(
                        label = "New Password",
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        isPasswordVisible = newPasswordVisible,
                        onVisibilityChange = { newPasswordVisible = !newPasswordVisible }
                    )
                    PasswordField(
                        label = "Confirm New Password",
                        value = confirmNewPassword,
                        onValueChange = { confirmNewPassword = it },
                        isPasswordVisible = confirmNewPasswordVisible,
                        onVisibilityChange = {
                            confirmNewPasswordVisible = !confirmNewPasswordVisible
                        }
                    )

                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }

                    errorMessage?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Button(
                        onClick = {
                            if (id.isEmpty()) {
                                errorMessage = "User ID not found. Please log in again."
                                return@Button
                            }

                            if (password.isBlank()) {
                                errorMessage = "Please enter the current password."
                                return@Button
                            }

                            if (newPassword.length < 6) {
                                errorMessage = "Password must be at least 6 characters long."
                                return@Button
                            }

                            if (newPassword == password) {
                                errorMessage =
                                    "New password cannot be the same as the current password."
                                return@Button
                            }

                            if (newPassword != confirmNewPassword) {
                                errorMessage = "Passwords do not match."
                                return@Button
                            }

                            isLoading = true

                            val data = EditPassword(password, newPassword)
                            vm.editPassword(
                                data,
                                id,
                                onSuccess = {
                                    isLoading = false

                                    sharedPreferencesManager.run {
                                        if (role == "USER") saveUserPassword(newPassword) else saveDocPassword(
                                            newPassword
                                        )
                                    }

                                    navController.navigate(if (role == "USER") Routes.UserHome.routes else Routes.DoctorHome.routes) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onError = { error ->
                                    isLoading = false
                                    errorMessage = when {
                                        error.contains(
                                            "Incorrect current password",
                                            ignoreCase = true
                                        ) -> "Incorrect current password. Please try again."

                                        error.contains(
                                            "User not found",
                                            ignoreCase = true
                                        ) -> "User not found. Please log in again."

                                        else -> "Failed to change password. Please try again."
                                    }
                                }
                            )
                        },
                        enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Change Password", style = MaterialTheme.typography.labelLarge)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityChange: () -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onVisibilityChange) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                )
            }
        },
        modifier = Modifier.padding(8.dp),
        singleLine = true
    )
}

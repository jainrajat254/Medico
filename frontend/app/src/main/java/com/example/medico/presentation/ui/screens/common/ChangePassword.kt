package com.example.medico.presentation.ui.screens.common

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medico.domain.model.EditPassword
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun ChangePassword(
    sharedPreferencesManager: SharedPreferencesManager,
    settingsViewModel: SettingsViewModel,
    navController: NavController,
) {
    val role = sharedPreferencesManager.getUserProfile()?.role ?: ""
    val id = if (role == "USER") {
        sharedPreferencesManager.getUserProfile()?.id
    } else {
        sharedPreferencesManager.getDocProfile()?.id
    }

    var password by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmNewPassword by rememberSaveable { mutableStateOf("") }

    // Visibility states
    var passwordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmNewPasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val editPasswordState by settingsViewModel.editPasswordState.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val isLoading = editPasswordState is ResultState.Loading // ✅ Use state directly

    LaunchedEffect(editPasswordState) {
        when (editPasswordState) {
            is ResultState.Success -> {
                Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }

            is ResultState.Error -> {
                val error = (editPasswordState as ResultState.Error).error.message
                    ?: "Failed to update password"
                errorMessage = when {
                    error.contains(
                        "Incorrect current password",
                        ignoreCase = true
                    ) -> "Current password is incorrect."

                    else -> error
                }
            }

            else -> Unit
        }
    }

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

                    Spacer(modifier = Modifier.height(12.dp))

                    errorMessage?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            errorMessage = null

                            when {
                                id.isNullOrEmpty() -> {
                                    errorMessage = "ID not found. Please log in again."
                                }

                                password.isBlank() -> {
                                    errorMessage = "Please enter the current password."
                                }

                                newPassword.length < 6 -> {
                                    errorMessage = "Password must be at least 6 characters long."
                                }

                                newPassword == password -> {
                                    errorMessage =
                                        "New password cannot be the same as the current password."
                                }

                                newPassword != confirmNewPassword -> {
                                    errorMessage = "Passwords do not match."
                                }

                                else -> {
                                    settingsViewModel.editPassword(
                                        data = EditPassword(password, newPassword),
                                        id = id
                                    )
                                }
                            }
                        },
                        enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
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

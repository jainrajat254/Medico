package com.example.medico.user.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medico.common.dto.EditPassword
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.UserPasswordField

@Composable
fun ChangePassword(
    sharedPreferencesManager: SharedPreferencesManager,
    vm: AuthViewModel,
    navController: NavController,
) {
    val user = sharedPreferencesManager.getUserFromSharedPreferences()
    val id = user?.id

    var password by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmNewPassword by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

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
                    UserPasswordField(
                        label = "Current Password",
                        value = password,
                        onValueChange = { password = it }
                    )
                    UserPasswordField(
                        label = "New Password",
                        value = newPassword,
                        onValueChange = { newPassword = it }
                    )
                    UserPasswordField(
                        label = "Confirm New Password",
                        value = confirmNewPassword,
                        onValueChange = { confirmNewPassword = it }
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
                            if (id.isNullOrEmpty()) {
                                Toast.makeText(
                                    context,
                                    "User ID not found. Please log in again.",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            // Validate current password
                            if (password.isBlank()) {
                                Toast.makeText(
                                    context,
                                    "Please enter the current password.",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            // Validate new password
                            if (newPassword.length < 6) {
                                Toast.makeText(
                                    context,
                                    "New password must be at least 6 characters long.",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            if (newPassword != confirmNewPassword) {
                                Toast.makeText(
                                    context,
                                    "New passwords do not match.",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            // Start loading
                            isLoading = true

                            val data = EditPassword(
                                password,
                                newPassword
                            )  // Pass current and new password
                            vm.editPassword(
                                data,
                                id,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Password Updated Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    sharedPreferencesManager.saveUserPassword(newPassword)

                                    isLoading = false
                                },
                                onError = { error ->
                                    isLoading = false
                                    val errorMessage =
                                        if (error.contains("Incorrect current password")) {
                                            "Incorrect current password"
                                        } else {
                                            "Password Changed Successfully"
                                        }

                                    Log.e("EditDetails", error)
                                    Toast.makeText(
                                        context,
                                        errorMessage,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )
                            navController.navigate(Routes.Settings.routes) {
                                popUpTo(Routes.UserHome.routes)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    )


                    {
                        Text(
                            text = "Change Password",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}


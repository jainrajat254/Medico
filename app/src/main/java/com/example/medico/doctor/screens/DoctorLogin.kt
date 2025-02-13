package com.example.medico.doctor.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.medico.common.model.LoginCredentials
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.LoginForm
import com.example.medico.common.viewModel.AuthViewModel

@Composable
fun LoginDoc(
    navController: NavController,
    context: Context,
    vm: AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {

            LoginForm(
                username = username,
                password = password,
                label = "Doctor Id",
                passwordVisible = passwordVisible,
                usernameError = usernameError,
                passwordError = passwordError,
                onUsernameChange = { username = it; usernameError = false },
                onPasswordChange = { password = it; passwordError = false },
                onPasswordToggle = { passwordVisible = !passwordVisible },
                onLogin = {
                    usernameError = username.isEmpty()
                    passwordError = password.isEmpty()

                    if (!usernameError && !passwordError) {
                        val user = LoginCredentials(username, password, "DOCTOR")
                        vm.loginDoc(
                            user,
                            onSuccess = { userResponse ->
                                sharedPreferencesManager.saveDocToPreferences(userResponse)
                                navController.navigate(Routes.Home.routes) {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            onError = {
                                Toast.makeText(
                                    context,
                                    "Invalid username or password. Please try again.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    }
                },
                onRegisterClick = { navController.navigate(Routes.DoctorRegister.routes) }
            )
        }
    }
}

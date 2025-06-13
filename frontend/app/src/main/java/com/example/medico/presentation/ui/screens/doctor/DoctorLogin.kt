package com.example.medico.presentation.ui.screens.doctor

import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.medico.domain.model.LoginCredentials
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.LoginForm
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun LoginDoc(
    navController: NavController,
    authViewModel: com.example.medico.presentation.viewmodel.AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val docLoginState by authViewModel.docLoginState.collectAsState()
    var isDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    LaunchedEffect(docLoginState) {
        when (docLoginState) {
            is ResultState.Success -> {
                isDialog = false
                val doc = (docLoginState as ResultState.Success).data
                sharedPreferencesManager.saveJwtToken(doc.token)
                sharedPreferencesManager.saveDocProfile(doc)
                navController.navigate(Routes.DoctorHome.routes) {
                    popUpTo(Routes.DoctorLogin.routes) { inclusive = true }
                }
            }

            is ResultState.Error -> {
                isDialog = false
                val message = (docLoginState as ResultState.Error).error
                Toast.makeText(context, message.toString(), Toast.LENGTH_LONG).show()
            }

            is ResultState.Loading -> isDialog = true
            is ResultState.Idle -> isDialog = false
        }
    }


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
                        authViewModel.loginDoc(
                            user,
                        )
                    }
                },
                onRegisterClick = { navController.navigate(Routes.DoctorRegister.routes) }
            )
            if (isDialog) {
                CustomLoader()
            }
        }
    }
}

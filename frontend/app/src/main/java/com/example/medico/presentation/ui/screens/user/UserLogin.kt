package com.example.medico.presentation.ui.screens.user

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.R
import com.example.medico.domain.model.LoginCredentials
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.LoginForm
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun LoginPage(
    navController: NavController,
    authViewModel: com.example.medico.presentation.viewmodel.AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val userLoginState by authViewModel.userLoginState.collectAsState()
    var isDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    LaunchedEffect(userLoginState) {
        when (userLoginState) {
            is ResultState.Success -> {
                isDialog = false
                val user = (userLoginState as ResultState.Success).data
                sharedPreferencesManager.saveUserProfile(user)
                navController.navigate(Routes.UserHome.routes) {
                    popUpTo(Routes.UserLogin.routes) { inclusive = true }
                }
            }

            is ResultState.Error -> {
                isDialog = false
                val message = (userLoginState as ResultState.Error).error
                Log.d("AUTH_ERROR",message.toString())
                Toast.makeText(context, message.toString(), Toast.LENGTH_LONG).show()
            }

            is ResultState.Loading -> isDialog = true
            is ResultState.Idle -> isDialog = false
        }
    }


    Scaffold { paddingValues ->
        BackgroundContent(paddingValues) {
            LoginForm(
                username = username,
                password = password,
                label = "Patient Id",
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
                        val user = LoginCredentials(username, password, "USER")
                        authViewModel.loginUser(user)
                    }
                },
                onRegisterClick = { navController.navigate(Routes.UserRegister.routes) }
            )

            if (isDialog) {
                CustomLoader()
            }
        }
    }

}


@Composable
fun SocialSignInButton() {
    Spacer(modifier = Modifier.height(20.dp))

    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .size(343.dp, 56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F1FA)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Google Icon
            Icon(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google Icon",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(5.dp))

            // Text next to the icon
            Text(
                text = stringResource(id = R.string.google),
                fontSize = 18.sp,
                color = Color(0XFF212325)
            )
        }
    }
}

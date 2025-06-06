package com.example.medico.presentation.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.medico.R
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.utils.SharedPreferencesManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app),
            contentDescription = "Logo",
            modifier = Modifier.size(300.dp)  // Adjust logo size as needed
        )
    }

    LaunchedEffect(Unit) {
        delay(2000)
        val isLoggedIn = sharedPreferencesManager.isLoggedIn()

        navController.navigate(
            if (isLoggedIn) Routes.UserHome.routes else Routes.UserLogin.routes
        ) {
            popUpTo(0) { inclusive = true }
        }
    }
}


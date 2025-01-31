package com.example.medico.pages

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
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    // Display logo immediately
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
        delay(2000)  // Wait for 2 seconds before navigating
        navController.popBackStack()
        navController.navigate("login") // Navigate to login screen
    }
}


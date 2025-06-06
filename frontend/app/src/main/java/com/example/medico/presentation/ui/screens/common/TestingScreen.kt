package com.example.medico.presentation.ui.screens.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.medico.presentation.ui.navigation.UserBottomNavBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TestingScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            UserBottomNavBar(modifier = Modifier, navController = navController)
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0) // ✅ Prevents extra padding in Scaffold

    ) { paddingValues ->
        // Apply paddingValues to the content to avoid overlapping with the bottomBar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()) // ✅ Handles bottom spacing correctly
        ) {
            Text(
                text = "Testing Screen",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
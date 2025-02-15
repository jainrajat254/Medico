package com.example.medico.user.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.medico.R
import com.example.medico.common.navigation.UserBottomNavBar
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent

@Composable
fun UserSettingsPage(
    navController: NavHostController,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    Scaffold(
        bottomBar = {
            UserBottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Text(
                text = "Settings",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val options = listOf(
                    "Personal Info",
                    "Address Details",
                    "FamilyDetails",
                    "Insurance Details",
                    "Change Password",
                    "App Theme",
                    "Notifications",
                    "Help & Support",
                    "Sign Out"
                )
                items(options) { option ->
                    SettingsOption(
                        title = option,
                        textColor = if (option == "Sign Out") Color.Red else Color.Black
                    ) {
                        when (option) {
                            "Sign Out" -> sharedPreferencesManager.logOut(navController)
                            "Personal Info" -> navController.navigate(Routes.UserPersonalDetails.routes)
                            "Insurance Details" -> navController.navigate(Routes.InsuranceDetails.routes)
                            "FamilyDetails" -> navController.navigate(Routes.FamilyDetails.routes)
                            "Address Details" -> navController.navigate(Routes.Address.routes)
                            "Change Password" -> navController.navigate(Routes.ChangePassword.routes)
                            "App Theme" -> navController.navigate("app_theme")
                            "Notifications" -> navController.navigate("notifications")
                            "Help & Support" -> navController.navigate("help_support")
                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsOption(title: String, textColor: Color = Color.Black, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = textColor
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
            Icon(
                painter = painterResource(id = R.drawable.forward), // Replace with forward arrow
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun AppThemeScreen(navController: NavHostController) {
    // Content for App Theme screen
}

@Composable
fun NotificationsScreen(navController: NavHostController) {
    // Content for Notifications screen
}

@Composable
fun HelpSupportScreen(navController: NavHostController) {
    // Content for Help & Support screen
}



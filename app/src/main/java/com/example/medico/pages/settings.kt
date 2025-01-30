package com.example.medico.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.medico.R
import com.example.medico.controllers.MyBottomBar
import com.example.medico.models.AuthViewModel

@Composable
fun SettingsPage(navController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val authState = authViewModel.authState.observeAsState()

    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            MyBottomBar(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.background_app),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                HeaderSection()

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Tagline()

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
                            "Change Password",
                            "App Theme",
                            "Notifications",
                            "Help & Support",
                            "Privacy Policy",
                            "Terms of Service",
                            "Sign Out"
                        )
                        items(options) { option ->
                            SettingsOption(
                                title = option,
                                textColor = if (option == "Sign Out") Color.Red else Color.Black
                            ) {
                                when (option) {
                                    "Sign Out" -> handleSignOut(context, navController, authViewModel)
                                    "Personal Info" -> navController.navigate("personal_info")
                                    "Change Password" -> navController.navigate("change_password")
                                    "App Theme" -> navController.navigate("app_theme")
                                    "Notifications" -> navController.navigate("notifications")
                                    "Help & Support" -> navController.navigate("help_support")
                                    "Privacy Policy" -> navController.navigate("privacy_policy")
                                    "Terms of Service" -> navController.navigate("terms_of_service")
                                    else -> Unit
                                }
                            }
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

private fun handleSignOut(context : Context, navController: NavHostController, authViewModel: AuthViewModel) {

    authViewModel.signout()
    Toast.makeText(context,"Signned Out Successfully",Toast.LENGTH_SHORT).show()
    navController.navigate("login") {
        popUpTo(0)
    }
}

@Composable
fun PersonalInfoScreen(navController: NavHostController) {
    // Content for Personal Info screen
}

@Composable
fun ChangePasswordScreen(navController: NavHostController) {
    // Content for Change Password screen
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

@Composable
fun PrivacyPolicyScreen(navController: NavHostController) {
    // Content for Privacy Policy screen
}

@Composable
fun TermsOfServiceScreen(navController: NavHostController) {
    // Content for Terms of Service screen
}


@Preview(showBackground = true)
@Composable
fun SettingsPagePreview() {
    val navController = rememberNavController()
    SettingsPage(navController = navController)
}

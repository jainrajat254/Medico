package com.example.medico.controllers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medico.models.AuthViewModel
import com.example.medico.pages.AddMedicationPage
import com.example.medico.pages.AppThemeScreen
import com.example.medico.pages.ChangePasswordScreen
import com.example.medico.pages.HealthRecords
import com.example.medico.pages.HealthReports
import com.example.medico.pages.HelpSupportScreen
import com.example.medico.pages.HomePage
import com.example.medico.pages.LoginPage
import com.example.medico.pages.MedicationPage
import com.example.medico.pages.NotificationsScreen
import com.example.medico.pages.PersonalInfoScreen
import com.example.medico.pages.PrivacyPolicyScreen
import com.example.medico.pages.Register
import com.example.medico.pages.SettingsPage
import com.example.medico.pages.SplashScreen
import com.example.medico.pages.TermsOfServiceScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun App() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val vm: AuthViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = Routes.Splash.routes) {
        composable(Routes.Splash.routes) {
            SplashScreen(navController = navController)
        }
        composable(Routes.Home.routes) {
            HomePage(navController)
        }
        composable(Routes.Medications.routes) {
            MedicationPage(navController)
        }
        composable(Routes.MedAdd.routes) {
            AddMedicationPage(navController)
        }
        composable(Routes.Records.routes) {
            HealthRecords(navController)
        }
        composable(Routes.Reports.routes) {
            HealthReports(navController)
        }
        composable(Routes.Settings.routes) {
            SettingsPage(navController)
        }
        composable(Routes.Login.routes) {
            LoginPage(navController, context, vm)
        }
        composable(Routes.Register.routes) {
            Register(navController, vm)
        }

        composable(Routes.PersonalInfo.routes) { PersonalInfoScreen(navController) }
        composable(Routes.ChangePassword.routes) { ChangePasswordScreen(navController) }
        composable(Routes.AppTheme.routes) { AppThemeScreen(navController) }
        composable(Routes.Notifications.routes) { NotificationsScreen(navController) }
        composable(Routes.HelpSupport.routes) { HelpSupportScreen(navController) }
        composable(Routes.PrivacyPolicy.routes) { PrivacyPolicyScreen(navController) }
        composable(Routes.TermsOfService.routes) { TermsOfServiceScreen(navController) }
    }
}


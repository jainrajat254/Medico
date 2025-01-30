package com.example.medico.controllers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import android.content.Context
import android.content.SharedPreferences

fun isFirstLaunch(context: Context): Boolean {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    var isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

    if (isFirstLaunch) {
        sharedPreferences.edit().putBoolean("isFirstLaunch", false)
            .apply()  // Set the flag to false after first launch
    }

    return isFirstLaunch
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {

    val context = LocalContext.current
    val startDestination = if (isFirstLaunch(context)) Routes.Splash.routes else Routes.Home.routes

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.Splash.routes) {
            SplashScreen(navController = navController)
        }
        composable(Routes.Home.routes) {
            HomePage(navController)
        }
        composable(Routes.BottomNav.routes) {
            BottomNav(navController)
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
            LoginPage(navController, context)
        }
        composable(Routes.Register.routes) {
            Register(navController)
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


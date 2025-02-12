package com.example.medico.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medico.models.AuthViewModel
import com.example.medico.pages.AddMedicationPage
import com.example.medico.pages.AppThemeScreen
import com.example.medico.pages.ChangePassword
import com.example.medico.pages.ContinueAs
import com.example.medico.pages.DocAddressDetails
import com.example.medico.pages.DocMedicalDetails
import com.example.medico.pages.DoctorRegister
import com.example.medico.pages.HealthRecords
import com.example.medico.pages.HealthReports
import com.example.medico.pages.HelpSupportScreen
import com.example.medico.pages.HomePage
import com.example.medico.pages.LoginDoc
import com.example.medico.pages.LoginPage
import com.example.medico.pages.MedicationPage
import com.example.medico.pages.NotificationsScreen
import com.example.medico.pages.PersonalInfoScreen
import com.example.medico.pages.PrivacyPolicyScreen
import com.example.medico.pages.Register
import com.example.medico.pages.SettingsPage
import com.example.medico.pages.SplashScreen
import com.example.medico.pages.TermsOfServiceScreen
import com.example.medico.pages.UserAccount
import com.example.medico.sharedPreferences.SharedPreferencesManager
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun App() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val vm: AuthViewModel = koinViewModel()
    val sharedPreferencesManager: SharedPreferencesManager = koinInject()


    NavHost(navController = navController, startDestination = Routes.ContinueAs.routes) {
        composable(Routes.Splash.routes) {
            SplashScreen(navController = navController, sharedPreferencesManager)
        }

        composable(Routes.ContinueAs.routes) {
            ContinueAs(navController)
        }
        composable(Routes.BottomNav.routes) {
            BottomNavBar(modifier = Modifier, navController = navController)
        }
        composable(Routes.Home.routes) {
            HomePage(navController, sharedPreferencesManager)
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
            SettingsPage(navController, sharedPreferencesManager)
        }
        composable(Routes.UserLogin.routes) {
            LoginPage(navController, context, vm, sharedPreferencesManager)
        }
        composable(Routes.Register.routes) {
            Register(navController, vm, sharedPreferencesManager)
        }

        composable(Routes.DoctorRegister.routes) {
            DoctorRegister(navController, vm)
        }

        composable(Routes.DocLogin.routes) {
            LoginDoc(navController, context, vm, sharedPreferencesManager)
        }

        composable(Routes.UserAccount.routes) {
            UserAccount(vm, sharedPreferencesManager)
        }

        composable(Routes.DocAddressDetails.routes) {
            DocAddressDetails(sharedPreferencesManager, vm)
        }

        composable(Routes.DocMedicalDetails.routes) {
            DocMedicalDetails(
                sharedPreferencesManager = sharedPreferencesManager,
                vm = vm,
                navController = navController
            )
        }

        composable(Routes.PersonalInfo.routes) { PersonalInfoScreen(navController) }
        composable(Routes.ChangePassword.routes) {
            ChangePassword(
                sharedPreferencesManager = sharedPreferencesManager,
                vm = vm,
                navController = navController
            )
        }
        composable(Routes.AppTheme.routes) { AppThemeScreen(navController) }
        composable(Routes.Notifications.routes) { NotificationsScreen(navController) }
        composable(Routes.HelpSupport.routes) { HelpSupportScreen(navController) }
        composable(Routes.PrivacyPolicy.routes) { PrivacyPolicyScreen(navController) }
        composable(Routes.TermsOfService.routes) { TermsOfServiceScreen(navController) }
    }
}


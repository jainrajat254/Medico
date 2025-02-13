package com.example.medico.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.doctor.screens.AddMedicationPage
import com.example.medico.common.screens.AppThemeScreen
import com.example.medico.user.screens.ChangePassword
import com.example.medico.common.screens.ContinueAs
import com.example.medico.doctor.screens.DocAddressDetails
import com.example.medico.doctor.screens.DocMedicalDetails
import com.example.medico.doctor.screens.DoctorRegister
import com.example.medico.user.screens.HealthRecords
import com.example.medico.user.screens.HealthReports
import com.example.medico.common.screens.HelpSupportScreen
import com.example.medico.user.screens.HomePage
import com.example.medico.doctor.screens.LoginDoc
import com.example.medico.user.screens.LoginPage
import com.example.medico.user.screens.MedicationPage
import com.example.medico.common.screens.NotificationsScreen
import com.example.medico.common.screens.PersonalInfoScreen
import com.example.medico.common.screens.PrivacyPolicyScreen
import com.example.medico.user.screens.Register
import com.example.medico.common.screens.SettingsPage
import com.example.medico.common.screens.SplashScreen
import com.example.medico.common.screens.TermsOfServiceScreen
import com.example.medico.user.screens.UserAccount
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.doctor.screens.HomeScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun App() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val vm: AuthViewModel = koinViewModel()
    val sharedPreferencesManager: SharedPreferencesManager = koinInject()


    NavHost(navController = navController, startDestination = Routes.DoctorHome.routes) {
        composable(Routes.Splash.routes) {
            SplashScreen(navController = navController, sharedPreferencesManager)
        }

        composable(Routes.Welcome.routes) {
            ContinueAs(navController)
        }
        composable(Routes.UserBottomNav.routes) {
            UserBottomNavBar(modifier = Modifier, navController = navController)
        }
        composable(Routes.UserHome.routes) {
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
        composable(Routes.UserRegister.routes) {
            Register(navController, vm, sharedPreferencesManager)
        }

        composable(Routes.DoctorRegister.routes) {
            DoctorRegister(navController, vm)
        }

        composable(Routes.DoctorHome.routes) {
            HomeScreen(navController = navController)
        }

        composable(Routes.DoctorLogin.routes) {
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


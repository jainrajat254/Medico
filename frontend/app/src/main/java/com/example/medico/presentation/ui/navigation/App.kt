package com.example.medico.presentation.ui.navigation

import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.DoctorDTO
import com.example.medico.domain.model.MedicationsDTO
import com.example.medico.presentation.ui.screens.common.ChangePassword
import com.example.medico.presentation.ui.screens.common.ContinueAs
import com.example.medico.presentation.ui.screens.common.SplashScreen
import com.example.medico.presentation.ui.screens.common.TestingScreen
import com.example.medico.presentation.ui.screens.doctor.AddMedicationPage
import com.example.medico.presentation.ui.screens.doctor.AddRecordScreen
import com.example.medico.presentation.ui.screens.doctor.AddReportScreen
import com.example.medico.presentation.ui.screens.doctor.AllAppointmentsScreen
import com.example.medico.presentation.ui.screens.doctor.AllMedications
import com.example.medico.presentation.ui.screens.doctor.AppThemeScreen
import com.example.medico.presentation.ui.screens.doctor.CurrentPatientInfo
import com.example.medico.presentation.ui.screens.doctor.DocAddressDetails
import com.example.medico.presentation.ui.screens.doctor.DocMedicalDetails
import com.example.medico.presentation.ui.screens.doctor.DocPersonalDetails
import com.example.medico.presentation.ui.screens.doctor.DoctorRegister
import com.example.medico.presentation.ui.screens.doctor.DoctorSettingsPage
import com.example.medico.presentation.ui.screens.doctor.HelpSupportScreen
import com.example.medico.presentation.ui.screens.doctor.HistoryScreen
import com.example.medico.presentation.ui.screens.doctor.HomeScreen
import com.example.medico.presentation.ui.screens.doctor.LoginDoc
import com.example.medico.presentation.ui.screens.doctor.NotificationsScreen
import com.example.medico.presentation.ui.screens.doctor.UserOverview
import com.example.medico.presentation.ui.screens.user.AddressDetails
import com.example.medico.presentation.ui.screens.user.BookAppointment
import com.example.medico.presentation.ui.screens.user.CurrentMedications
import com.example.medico.presentation.ui.screens.user.FamilyDetails
import com.example.medico.presentation.ui.screens.user.HealthReports
import com.example.medico.presentation.ui.screens.user.InsuranceDetails
import com.example.medico.presentation.ui.screens.user.LoginPage
import com.example.medico.presentation.ui.screens.user.Records
import com.example.medico.presentation.ui.screens.user.UserRegister
import com.example.medico.presentation.ui.screens.user.UserSettingsPage
import com.example.medico.presentation.ui.screens.user.DoctorAppointmentScreen
import com.example.medico.presentation.ui.screens.user.DoctorOverview
import com.example.medico.presentation.ui.screens.user.MedicationPage
import com.example.medico.presentation.ui.screens.user.UserHomePage
import com.example.medico.presentation.ui.screens.user.UserPersonalDetails
import com.example.medico.presentation.viewmodel.MedicationsViewModel
import com.example.medico.presentation.viewmodel.ReportsViewModel
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.SharedPreferencesManager
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun App() {

    val navController = rememberNavController()
    val sharedPreferencesManager: SharedPreferencesManager = koinInject()

    val authViewModel: com.example.medico.presentation.viewmodel.AuthViewModel = koinViewModel()
    val appointmentsViewModel: com.example.medico.presentation.viewmodel.AppointmentsViewModel =
        koinViewModel()
    val medicationsViewModel: MedicationsViewModel = koinViewModel()
    val recordsViewModel: com.example.medico.presentation.viewmodel.RecordsViewModel =
        koinViewModel()
    val reportsViewModel: ReportsViewModel = koinViewModel()
    val settingsViewModel: SettingsViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = Routes.Welcome.routes) {
        composable(Routes.Splash.routes) {
            SplashScreen(navController = navController, sharedPreferencesManager)
        }

        composable("Testing") {
            TestingScreen(navController = navController)
        }

        composable(Routes.Welcome.routes) {
            ContinueAs(navController)
        }
        composable(Routes.UserBottomNav.routes) {
            UserBottomNavBar(modifier = Modifier, navController = navController)
        }
        composable(Routes.UserHome.routes) {
            UserHomePage(
                navController = navController,
                sharedPreferencesManager = sharedPreferencesManager,
                medicationsViewModel = medicationsViewModel,
                appointmentsViewModel = appointmentsViewModel
            )
        }
        composable(Routes.Medications.routes) {
            MedicationPage(
                navController = navController,
                sharedPreferencesManager = sharedPreferencesManager,
                medicationsViewModel = medicationsViewModel
            )
        }
        composable("add_medications/{userDetails}") { backStackEntry ->
            val userJsonEncoded = backStackEntry.arguments?.getString("userDetails")
            if (userJsonEncoded != null) {
                val decodedJson =
                    String(Base64.decode(userJsonEncoded, Base64.URL_SAFE or Base64.NO_WRAP))
                val userDetails = Json.decodeFromString<AppointmentDTO>(decodedJson)
                AddMedicationPage(
                    navController = navController,
                    userDetails = userDetails,
                    medicationsViewModel = medicationsViewModel,
                    sharedPreferencesManager = sharedPreferencesManager
                )
            }
        }
        composable(Routes.CurrentMed.routes) {
            CurrentMedications(
                sharedPreferencesManager = sharedPreferencesManager,
                medicationsViewModel = medicationsViewModel
            )
        }
        composable(Routes.Reports.routes) {
            HealthReports(
                navController = navController,
                sharedPreferencesManager = sharedPreferencesManager,
                reportsViewModel = reportsViewModel
            )
        }
        composable(Routes.UserAppointments.routes) {
            DoctorAppointmentScreen(
                navController = navController,
                settingsViewModel = settingsViewModel
            )
        }
        composable(Routes.UserSettings.routes) {
            UserSettingsPage(
                navController = navController,
                sharedPreferencesManager = sharedPreferencesManager
            )
        }
        composable(Routes.DocSettings.routes) {
            DoctorSettingsPage(
                navController = navController,
                sharedPreferencesManager = sharedPreferencesManager
            )
        }
        composable(Routes.UserLogin.routes) {
            LoginPage(
                navController = navController,
                authViewModel = authViewModel,
                sharedPreferencesManager = sharedPreferencesManager
            )
        }
        composable(Routes.UserRegister.routes) {
            UserRegister(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Routes.DoctorRegister.routes) {
            DoctorRegister(navController = navController, authViewModel = authViewModel)
        }

        composable(Routes.AllAppointmentsScreen.routes) {
            AllAppointmentsScreen(
                appointmentsViewModel = appointmentsViewModel,
                navController = navController,
                sharedPreferencesManager = sharedPreferencesManager
            )
        }

        composable(Routes.History.routes) {
            HistoryScreen(
                appointmentsViewModel = appointmentsViewModel,
                navController = navController,
                sharedPreferencesManager = sharedPreferencesManager
            )
        }

        composable("update_med/{medication}") { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("medication")
            if (encodedJson != null) {
                val decodedJson =
                    String(Base64.decode(encodedJson, Base64.URL_SAFE or Base64.NO_WRAP))
                val existingMedication = Json.decodeFromString<MedicationsDTO>(decodedJson)

                AddMedicationPage(
                    navController = navController,
                    existingMedication = existingMedication,
                    sharedPreferencesManager = sharedPreferencesManager,
                    medicationsViewModel = medicationsViewModel
                ) // Pass the existing medication
            }
        }

        composable("add_report/{current_patient}") { backStackEntry ->
            val userJsonEncoded = backStackEntry.arguments?.getString("current_patient")
            if (userJsonEncoded != null) {
                val decodedJson =
                    String(Base64.decode(userJsonEncoded, Base64.URL_SAFE or Base64.NO_WRAP))
                val userDetails = Json.decodeFromString<AppointmentDTO>(decodedJson)
                AddReportScreen(
                    navController = navController,
                    sharedPreferencesManager = sharedPreferencesManager,
                    userDetails = userDetails,
                    reportsViewModel = reportsViewModel
                )
            }
        }

        composable("add_record/{current_patient}") { backStackEntry ->
            val userJsonEncoded = backStackEntry.arguments?.getString("current_patient")
            if (userJsonEncoded != null) {
                val decodedJson =
                    String(Base64.decode(userJsonEncoded, Base64.URL_SAFE or Base64.NO_WRAP))
                val userDetails = Json.decodeFromString<AppointmentDTO>(decodedJson)
                AddRecordScreen(
                    navController = navController,
                    recordsViewModel = recordsViewModel,
                    userDetails = userDetails,
                    sharedPreferencesManager = sharedPreferencesManager
                )
            }
        }

        composable(Routes.DoctorHome.routes) {
            HomeScreen(
                navController = navController,
                appointmentsViewModel = appointmentsViewModel,
                sharedPreferencesManager = sharedPreferencesManager
            )
        }

        composable("current_patient/{userDetails}/{index}") { backStackEntry ->
            val userJsonEncoded = backStackEntry.arguments?.getString("userDetails")
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()

            if (userJsonEncoded != null && index != null) {
                val decodedJson =
                    String(Base64.decode(userJsonEncoded, Base64.URL_SAFE or Base64.NO_WRAP))
                val userDetails = Json.decodeFromString<AppointmentDTO>(decodedJson)
                CurrentPatientInfo(
                    userDetails = userDetails,
                    index = index,
                    sharedPreferencesManager = sharedPreferencesManager,
                    recordsViewModel = recordsViewModel,
                    reportsViewModel = reportsViewModel,
                    medicationsViewModel = medicationsViewModel,
                    navController = navController
                )
            }
        }

        composable(Routes.UserOverview.routes) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                UserOverview(id = id, settingsViewModel = settingsViewModel)
            }
        }

        composable(Routes.AllMedications.routes) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                AllMedications(
                    userId = id,
                    medicationsViewModel = medicationsViewModel,
                )
            }
        }

        composable(Routes.InsuranceDetails.routes) {
            InsuranceDetails(
                settingsViewModel = settingsViewModel,
                sharedPreferencesManager = sharedPreferencesManager
            )
        }

        composable(Routes.FamilyDetails.routes) {
            FamilyDetails(
                sharedPreferencesManager = sharedPreferencesManager,
                settingsViewModel = settingsViewModel
            )
        }

        composable(Routes.Address.routes) {
            AddressDetails(
                sharedPreferencesManager = sharedPreferencesManager,
                settingsViewModel = settingsViewModel
            )
        }

        composable(Routes.Records.routes) {
            Records(
                navController = navController,
                recordsViewModel = recordsViewModel,
                sharedPreferencesManager = sharedPreferencesManager
            )
        }

        composable(Routes.DoctorLogin.routes) {
            LoginDoc(
                navController = navController,
                authViewModel = authViewModel,
                sharedPreferencesManager = sharedPreferencesManager,
            )
        }

        composable(Routes.UserPersonalDetails.routes) {
            UserPersonalDetails(
                sharedPreferencesManager = sharedPreferencesManager,
                settingsViewModel = settingsViewModel
            )
        }

        composable(Routes.DocAddressDetails.routes) {
            DocAddressDetails(
                sharedPreferencesManager = sharedPreferencesManager,
                settingsViewModel = settingsViewModel
            )
        }

        composable(Routes.DoctorOverview.routes) { backStackEntry ->
            val doctorJson = backStackEntry.arguments?.getString("doctorDetails")
            if (doctorJson != null) {
                val doctorDetails = Json.decodeFromString<DoctorDTO>(doctorJson)
                DoctorOverview(doctorDetails)
            }
        }

        composable(Routes.BookAppointment.routes) { backStackEntry ->
            val doctorJson = backStackEntry.arguments?.getString("doctorDetails")
            if (doctorJson != null) {
                val doctorDetails = Json.decodeFromString<DoctorDTO>(doctorJson)
                BookAppointment(
                    navController = navController,
                    sharedPreferencesManager = sharedPreferencesManager,
                    doctorDetails = doctorDetails,
                    appointmentsViewModel = appointmentsViewModel
                )
            }
        }

        composable(Routes.DocMedicalDetails.routes) {
            DocMedicalDetails(
                sharedPreferencesManager = sharedPreferencesManager,
                settingsViewModel = settingsViewModel
            )
        }
        composable(Routes.DocPersonalDetails.routes) {
            DocPersonalDetails(
                sharedPreferencesManager = sharedPreferencesManager,
                settingsViewModel = settingsViewModel
            )
        }
        composable(Routes.ChangePassword.routes) {
            ChangePassword(
                sharedPreferencesManager = sharedPreferencesManager,
                settingsViewModel = settingsViewModel,
                navController = navController
            )
        }
        composable(Routes.AppTheme.routes) { AppThemeScreen(navController) }
        composable(Routes.Notifications.routes) { NotificationsScreen(navController) }
        composable(Routes.HelpSupport.routes) { HelpSupportScreen(navController) }
    }
}

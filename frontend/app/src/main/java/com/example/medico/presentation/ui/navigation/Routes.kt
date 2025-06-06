package com.example.medico.presentation.ui.navigation

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.DoctorDTO
import com.example.medico.domain.model.MedicationsDTO
import kotlinx.serialization.json.Json

sealed class Routes(var routes: String) {

    data object Splash : Routes("splash")
    data object Welcome : Routes("welcome")

    data object UserLogin : Routes("user_login")
    data object UserPersonalDetails : Routes("user_personal_details")
    data object UserRegister : Routes("user_register")
    data object UserHome : Routes("user_home")
    data object Address : Routes("address")
    data object Records : Routes("records")
    data object Medications : Routes("medications")
    data object MedAdd : Routes("add_medications/{current_patient}") {
        @RequiresApi(Build.VERSION_CODES.FROYO)
        fun createRoutes(userDetails: AppointmentDTO): String {
            val json = Json.encodeToString(userDetails)
            val encodedJson =
                Base64.encodeToString(json.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
            return "add_medications/$encodedJson"
        }
    }
    data object CurrentMed : Routes("currentMed")
    data object AddRecord : Routes("add_record/{current_patient}") {
        @RequiresApi(Build.VERSION_CODES.FROYO)
        fun createRoutes(userDetails: AppointmentDTO): String {
            val json = Json.encodeToString(userDetails)
            val encodedJson =
                Base64.encodeToString(json.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
            return "add_record/$encodedJson"
        }
    }
    data object Reports : Routes("reports")
    data object UserSettings : Routes("user_settings")
    data object History : Routes("history")
    data object UserAppointments : Routes("user_appointments")
    data object UserBottomNav : Routes("user_bottom_nav")
    data object ChangePassword : Routes("change_password")
    data object AppTheme : Routes("app_theme")
    data object Notifications : Routes("notifications")
    data object HelpSupport : Routes("help_support")

    data object DoctorHome : Routes("doctor_home")
    data object CurrentPatient : Routes("current_patient/{userDetails}/{index}") {
        fun createRoutes(userDetails: AppointmentDTO, index: Int): String {
            val json = Json.encodeToString(userDetails)
            val encodedJson =
                Base64.encodeToString(json.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
            return "current_patient/$encodedJson/$index"
        }
    }
    data object UserOverview : Routes("user_overview/{id}") {
        fun createRoutes(id: String) =
            "user_overview/$id"
    }

    data object AllMedications : Routes("all_medications/{id}") {
        fun createRoutes(id: String) =
            "all_medications/$id"
    }
    data object DocSettings : Routes("doc_settings")
    data object InsuranceDetails : Routes("insurance_details")
    data object FamilyDetails : Routes("family_details")
    data object DocPersonalDetails : Routes("doc_personal_details")
    data object DocAddressDetails : Routes("doc_address_details")
    data object DocMedicalDetails : Routes("doc_medical_details")
    data object DoctorOverview : Routes("doctor_overview/{doctorDetails}") {
        fun createRoutes(doctorDetails: DoctorDTO) =
            "doctor_overview/${Json.encodeToString(doctorDetails)}"
    }
    data object BookAppointment : Routes("book_appointment/{doctorDetails}") {
        fun createRoutes(doctorDetails: DoctorDTO) =
            "book_appointment/${Json.encodeToString(doctorDetails)}"
    }
    data object AllAppointmentsScreen : Routes("all_appointments")

    data object AddReport : Routes("add_report/{current_patient}") {
        fun createRoutes(userDetails: AppointmentDTO): String {
            val json = Json.encodeToString(userDetails)
            val encodedJson =
                Base64.encodeToString(json.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
            return "add_report/$encodedJson"
        }
    }


    data object UpdateMed : Routes("update_med/{medication}") {
        fun createRoute(medication: MedicationsDTO): String {
            val json = Json.encodeToString(medication)
            val encodedJson = Base64.encodeToString(json.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
            return "update_med/$encodedJson"
        }
    }


    data object DoctorRegister : Routes("doctor_register")
    data object DoctorLogin : Routes("doctor_login")
}

package com.example.medico.common.navigation

import com.example.medico.doctor.dto.DoctorDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class Routes(var routes: String) {

    data object Splash : Routes("splash")
    data object Welcome : Routes("welcome")

    data object UserLogin : Routes("user_login")
    data object UserPersonalDetails : Routes("user_personal_details")
    data object UserRegister : Routes("user_register")
    data object UserHome : Routes("user_home")
    data object Address : Routes("address")
    data object Medications : Routes("medications")
    data object MedAdd : Routes("add_medications")
    data object Records : Routes("records")
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
    data object CurrentPatient : Routes("current_patient")
    data object UserOverview : Routes("user_overview/{id}") {
        fun createRoutes(id: String) =
            "user_overview/$id"
    }
    data object DocSettings : Routes("doc_settings")
    data object DoctorAppointments : Routes("doc_appointments")
    data object Schedule : Routes("schedule")
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

    data object DoctorRegister : Routes("doctor_register")
    data object DoctorLogin : Routes("doctor_login")
}

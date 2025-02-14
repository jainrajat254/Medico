package com.example.medico.common.navigation

object Routes {
    val Splash = Route("splash")
    val Welcome = Route("welcome")
    val UserLogin = Route("user_login")
    val UserAccount = Route("user_account")
    val UserRegister = Route("user_register")
    val UserHome = Route("user_home")
    val DoctorHome = Route("doctor_home")
    val CurrentPatient = Route("current_patient")
    val CurrentPatientDetails = Route("current_patient_details")
    val Medications = Route("medications")
    val MedAdd = Route("add_medications")
    val Records = Route("records")
    val Reports = Route("reports")
    val Settings = Route("settings")
    val History = Route("history")
    val Schedule = Route("schedule")
    val UserBottomNav = Route("user_bottom_nav")
    val DocAddressDetails = Route("doc_address_details")
    val DocMedicalDetails = Route("doc_medical_details")

    val PersonalInfo = Route("personal_info")
    val ChangePassword = Route("change_password")
    val AppTheme = Route("app_theme")
    val Notifications = Route("notifications")
    val HelpSupport = Route("help_support")
    val PrivacyPolicy = Route("privacy_policy")
    val TermsOfService = Route("terms_of_service")

    val DoctorRegister = Route("doctor_register")
    val DoctorLogin = Route("doctor_login")

    data class Route(val routes: String)
}

package com.example.medico.common.navigation

object Routes {
    val Splash = Route("splash")
    val ContinueAs = Route("continue_as")
    val UserLogin = Route("user_login")
    val UserAccount = Route("user_account")
    val Register = Route("register")
    val Home = Route("home")
    val Medications = Route("medications")
    val MedAdd = Route("add_medications")
    val Records = Route("records")
    val Reports = Route("reports")
    val Settings = Route("settings")
    val BottomNav = Route("bottom_nav")
    val DocAddressDetails = Route("doc_address_details")
    val DocMedicalDetails = Route("doc_medical_details")


    // New routes for settings options
    val PersonalInfo = Route("personal_info")
    val ChangePassword = Route("change_password")
    val AppTheme = Route("app_theme")
    val Notifications = Route("notifications")
    val HelpSupport = Route("help_support")
    val PrivacyPolicy = Route("privacy_policy")
    val TermsOfService = Route("terms_of_service")

    val DoctorRegister = Route("doctor_register")
    val DocLogin = Route("doc_login")

    data class Route(val routes: String)
}

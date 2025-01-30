package com.example.medico.controllers

object Routes {
    val Splash = Route("splash")
    val Login = Route("login")
    val Register = Route("register")
    val Home = Route("home")
    val Medications = Route("medications")
    val MedAdd = Route("add_medications")
    val Records = Route("records")
    val Reports = Route("reports")
    val Settings = Route("settings")
    val BottomNav = Route("bottom_nav")

    // New routes for settings options
    val PersonalInfo = Route("personal_info")
    val ChangePassword = Route("change_password")
    val AppTheme = Route("app_theme")
    val Notifications = Route("notifications")
    val HelpSupport = Route("help_support")
    val PrivacyPolicy = Route("privacy_policy")
    val TermsOfService = Route("terms_of_service")

    data class Route(val routes: String)
}

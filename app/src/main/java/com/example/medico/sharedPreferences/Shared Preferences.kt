package com.example.medico.sharedPreferences

import android.content.Context
import com.example.medico.data.LoginResponse

object SharedPreferencesManager {
    private const val PREFS_NAME = "UserPrefs"
    private const val PREFS_EMAIL = "email"
    private const val IS_LOGGED_IN_KEY = "IS_LOGGED_IN"
    private const val JWT_TOKEN_KEY = "jwt_token"
    private const val PREFS_FIRST_NAME = "firstName"
    private const val PREFS_LAST_NAME = "lastName"
    private const val PREFS_AGE = "age"
    private const val PREFS_GENDER = "gender"
    private const val PREFS_BLOOD_GROUP = "bloodGroup"
    private const val PREFS_PHONE = "phone"


    fun saveUserToPreferences(context: Context, userResponse: LoginResponse) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply() {
            putString(PREFS_EMAIL, userResponse.email)
            putBoolean(IS_LOGGED_IN_KEY, true)
            putString(JWT_TOKEN_KEY, userResponse.token)
            apply()
        }
    }

    fun getJwtToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(JWT_TOKEN_KEY, null)
    }

    fun getUserFromPreferences(context: Context): LoginResponse? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userFirstName = sharedPreferences.getString(PREFS_FIRST_NAME, null)
        val userLastName = sharedPreferences.getString(PREFS_LAST_NAME, null)
        val age = sharedPreferences.getString(PREFS_AGE, null)
        val gender = sharedPreferences.getString(PREFS_GENDER, null)
        val bloodGroup = sharedPreferences.getString(PREFS_BLOOD_GROUP, null)
        val phone = sharedPreferences.getString(PREFS_PHONE, null)
        val email = sharedPreferences.getString(PREFS_EMAIL, null)
        val token = sharedPreferences.getString(JWT_TOKEN_KEY, null)

        return if (userFirstName != null && userLastName != null && age != null && gender != null && bloodGroup != null && phone != null && email != null && token != null) {
            LoginResponse(token, userFirstName, userLastName, age, gender, bloodGroup, phone, email)
        } else {
            null
        }
    }

    fun logOut(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            clear()
            apply()
        }
    }
}
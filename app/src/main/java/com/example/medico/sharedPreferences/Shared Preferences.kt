package com.example.medico.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import com.example.medico.data.LoginResponse
import com.example.medico.navigation.Routes
import java.util.UUID

class SharedPreferencesManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val PREFS_EMAIL = "email"
        private const val IS_LOGGED_IN_KEY = "IS_LOGGED_IN"
        private const val JWT_TOKEN_KEY = "jwt_token"
        private const val PREFS_ID = "id"
        private const val PREFS_FIRST_NAME = "firstName"
        private const val PREFS_LAST_NAME = "lastName"
        private const val PREFS_AGE = "age"
        private const val PREFS_GENDER = "gender"
        private const val PREFS_BLOOD_GROUP = "bloodGroup"
        private const val PREFS_PHONE = "phone"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserToPreferences(userResponse: LoginResponse) {
        sharedPreferences.edit().apply {
            putString(PREFS_FIRST_NAME, userResponse.firstName)
            putString(PREFS_LAST_NAME, userResponse.lastName)
            putString(PREFS_AGE, userResponse.age)
            putString(PREFS_GENDER, userResponse.gender)
            putString(PREFS_BLOOD_GROUP, userResponse.bloodGroup)
            putString(PREFS_PHONE, userResponse.phone)
            putString(PREFS_EMAIL, userResponse.email)
            putBoolean(IS_LOGGED_IN_KEY, true)
            putString(JWT_TOKEN_KEY, userResponse.token)
            putString(PREFS_ID, userResponse.id) // Save UUID
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)
    }

    fun getJwtToken(): String? {
        return sharedPreferences.getString(JWT_TOKEN_KEY, null)
    }

    fun getUserFromSharedPreferences(): LoginResponse? {
        val userFirstName = sharedPreferences.getString(PREFS_FIRST_NAME, null)
        val userLastName = sharedPreferences.getString(PREFS_LAST_NAME, null)
        val age = sharedPreferences.getString(PREFS_AGE, null)
        val gender = sharedPreferences.getString(PREFS_GENDER, null)
        val bloodGroup = sharedPreferences.getString(PREFS_BLOOD_GROUP, null)
        val phone = sharedPreferences.getString(PREFS_PHONE, null)
        val email = sharedPreferences.getString(PREFS_EMAIL, null)
        val token = sharedPreferences.getString(JWT_TOKEN_KEY, null)
        val id = sharedPreferences.getString(PREFS_ID, null)

        return if (userFirstName != null && userLastName != null && age != null &&
            gender != null && bloodGroup != null && phone != null &&
            email != null && token != null && id != null
        ) {
            LoginResponse(token, id, userFirstName, userLastName, age, gender, bloodGroup, phone, email)
        } else {
            null
        }
    }

    fun logOut(navController: NavController) {
        sharedPreferences.edit().apply {
            clear()
            apply()
        }
        navController.navigate(Routes.UserLogin.routes)
    }

    // Extension function to store UUID
    private fun SharedPreferences.Editor.putUUID(key: String, uuid: UUID?) {
        putString(key, uuid?.toString())
    }

    // Extension function to retrieve UUID
    private fun SharedPreferences.getUUID(key: String, default: UUID? = null): UUID? {
        val uuidString = this.getString(key, default?.toString())
        return uuidString?.let { UUID.fromString(it) }
    }
}

package com.example.medico.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import com.example.medico.domain.model.DoctorLoginResponse
import com.example.medico.domain.model.GetExtraDetailsId
import com.example.medico.domain.model.UserDetailsResponse
import com.example.medico.domain.model.UserLoginResponse
import com.example.medico.presentation.ui.navigation.Routes
import kotlinx.serialization.json.Json
import java.util.UUID

class SharedPreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val IS_LOGGED_IN_KEY = "IS_LOGGED_IN"
        private const val JWT_TOKEN_KEY = "jwt_token"

        private const val USER_PROFILE_DATA = "user_profile_data"
        private const val DOC_PROFILE_DATA = "doc_profile_data"
        private const val USER_DETAILS = "user-details"

        private const val PREFS_FIRST_NAME = "user_firstName"
        private const val PREFS_LAST_NAME = "user_lastName"
        private const val PREFS_USER_EXTRA_DETAILS_ID = "extra_details_id"

    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)
    }

    fun userLogout(navController: NavController) {
        sharedPreferences.edit().apply {
            clear()
            apply()
        }
        navController.navigate(Routes.UserLogin.routes) {
            popUpTo(0)
        }
    }

    fun docLogout(navController: NavController) {
        sharedPreferences.edit().apply {
            clear()
            apply()
        }
        navController.navigate(Routes.DoctorLogin.routes) {
            popUpTo(0)
        }
    }

    fun getUserName(): String {
        val firstName = sharedPreferences.getString(PREFS_FIRST_NAME, "") ?: ""
        val lastName = sharedPreferences.getString(PREFS_LAST_NAME, "") ?: ""
        return "$firstName $lastName".trim()
    }

    fun saveJwtToken(token: String) {
        return sharedPreferences.edit().putString(JWT_TOKEN_KEY, token).apply()
    }

    fun getJwtToken(): String? {
        return sharedPreferences.getString(JWT_TOKEN_KEY, null)
    }

    fun saveUserProfile(profile: UserLoginResponse) {
        val profileJson = json.encodeToString(UserLoginResponse.serializer(), profile)
        prefs.edit().putString(USER_PROFILE_DATA, profileJson).apply()
    }

    fun saveDocProfile(profile: DoctorLoginResponse) {
        val profileJson = json.encodeToString(DoctorLoginResponse.serializer(), profile)
        prefs.edit().putString(DOC_PROFILE_DATA, profileJson).apply()
    }

    fun getUserProfile(): UserLoginResponse? {
        val jsonString = prefs.getString(USER_PROFILE_DATA, null) ?: return null
        return try {
            json.decodeFromString(UserLoginResponse.serializer(), jsonString)
        } catch (e: Exception) {
            null
        }
    }

    fun getDocProfile(): DoctorLoginResponse? {
        val jsonString = prefs.getString(DOC_PROFILE_DATA, null) ?: return null
        return try {
            json.decodeFromString(DoctorLoginResponse.serializer(), jsonString)
        } catch (e: Exception) {
            null
        }
    }


    fun saveUserExtraDetails(userDetailsResponse: UserDetailsResponse) {
        val profileJson = json.encodeToString(UserDetailsResponse.serializer(), userDetailsResponse)
        prefs.edit().putString(USER_DETAILS, profileJson).apply()
    }

    fun getUserExtraDetails(): UserDetailsResponse? {
        val jsonString = prefs.getString(USER_DETAILS, null) ?: return null
        return try {
            json.decodeFromString(UserDetailsResponse.serializer(), jsonString)
        } catch (e: Exception) {
            null
        }
    }

    fun saveUserExtraDetailsId(data: GetExtraDetailsId) {
        val profileJson = json.encodeToString(GetExtraDetailsId.serializer(), data)
        prefs.edit().putString(PREFS_USER_EXTRA_DETAILS_ID, profileJson).apply()
    }

    fun getUserExtraDetailsId(): GetExtraDetailsId? {
        val jsonString = prefs.getString(PREFS_USER_EXTRA_DETAILS_ID, null) ?: return null
        return try {
            json.decodeFromString(GetExtraDetailsId.serializer(), jsonString)
        } catch (e: Exception) {
            null
        }
    }
}


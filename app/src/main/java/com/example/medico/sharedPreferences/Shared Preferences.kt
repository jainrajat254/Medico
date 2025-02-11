package com.example.medico.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import com.example.medico.data.DoctorResponse
import com.example.medico.data.LoginResponse
import com.example.medico.navigation.Routes
import java.util.UUID

class SharedPreferencesManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val IS_LOGGED_IN_KEY = "IS_LOGGED_IN"
        private const val JWT_TOKEN_KEY = "jwt_token"

        private const val PREFS_EMAIL = "email"
        private const val PREFS_ID = "id"
        private const val PREFS_FIRST_NAME = "firstName"
        private const val PREFS_LAST_NAME = "lastName"
        private const val PREFS_AGE = "age"
        private const val PREFS_GENDER = "gender"
        private const val PREFS_BLOOD_GROUP = "bloodGroup"
        private const val PREFS_PHONE = "phone"

        private const val DOC_PREFS_ID = "id"
        private const val DOC_PREFS_ADDRESS = "address"
        private const val DOC_PREFS_AVAILABLE_FOR_ONLINE_CONSULTATION =
            "availableForOnlineConsultation"
        private const val DOC_PREFS_DISTRICT = "district"
        private const val DOC_PREFS_DOB = "dob"
        private const val DOC_PREFS_EMAIL = "email"
        private const val DOC_PREFS_EXPERIENCE = "experience"
        private const val DOC_PREFS_FEE = "fee"
        private const val DOC_PREFS_FIRST_NAME = "firstName"
        private const val DOC_PREFS_GENDER = "gender"
        private const val DOC_PREFS_LAST_NAME = "lastName"
        private const val DOC_PREFS_MEDICAL_REG_NO = "medicalRegNo"
        private const val DOC_PREFS_PHONE = "phone"
        private const val DOC_PREFS_QUALIFICATION = "qualification"
        private const val DOC_PREFS_SPECIALIZATION = "specialization"
        private const val DOC_PREFS_STATE = "state"
        private const val DOC_PREFS_UID = "uid"
        private const val DOC_PREFS_WORKING_TIME = "workingTime"
        private const val DOC_PREFS_WORKSPACE_NAME = "workspaceName"
        private const val DOC_PREFS_ZIP_CODE = "zipCode"

    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveDocToPreferences(userResponse: DoctorResponse) {
        sharedPreferences.edit().apply {
            putBoolean(IS_LOGGED_IN_KEY, true)
            putString(JWT_TOKEN_KEY, userResponse.token)
            putString(DOC_PREFS_FIRST_NAME, userResponse.firstName)
            putString(DOC_PREFS_LAST_NAME, userResponse.lastName)
            putString(DOC_PREFS_DOB, userResponse.dob)
            putString(DOC_PREFS_GENDER, userResponse.gender)
            putString(DOC_PREFS_PHONE, userResponse.phone)
            putString(DOC_PREFS_EMAIL, userResponse.email)
            putString(DOC_PREFS_ID, userResponse.id)
            putString(DOC_PREFS_UID, userResponse.uid)
            putString(DOC_PREFS_ADDRESS, userResponse.address)
            putBoolean(
                DOC_PREFS_AVAILABLE_FOR_ONLINE_CONSULTATION,
                userResponse.availableForOnlineConsultation
            )
            putString(DOC_PREFS_STATE, userResponse.state)
            putString(DOC_PREFS_DISTRICT, userResponse.district)
            putString(DOC_PREFS_QUALIFICATION, userResponse.qualification)
            putString(DOC_PREFS_SPECIALIZATION, userResponse.specialization)
            putString(DOC_PREFS_MEDICAL_REG_NO, userResponse.medicalRegNo)
            putString(DOC_PREFS_WORKSPACE_NAME, userResponse.workspaceName)
            putString(DOC_PREFS_ZIP_CODE, userResponse.zipCode)
            putString(DOC_PREFS_WORKING_TIME, userResponse.workingTime.toString())
            putInt(DOC_PREFS_EXPERIENCE, userResponse.experience)
            putInt(DOC_PREFS_FEE, userResponse.fee)
            apply()
        }
    }

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

    fun getDocFromSharedPreferences(): DoctorResponse? {
        val userFirstName = sharedPreferences.getString(DOC_PREFS_FIRST_NAME, null)
        val userLastName = sharedPreferences.getString(DOC_PREFS_LAST_NAME, null)
        val dob = sharedPreferences.getString(DOC_PREFS_DOB, null)
        val gender = sharedPreferences.getString(DOC_PREFS_GENDER, null)
        val phone = sharedPreferences.getString(DOC_PREFS_PHONE, null)
        val email = sharedPreferences.getString(DOC_PREFS_EMAIL, null)
        val token = sharedPreferences.getString(JWT_TOKEN_KEY, null)
        val id = sharedPreferences.getString(DOC_PREFS_ID, null)
        val uid = sharedPreferences.getString(DOC_PREFS_UID, null)
        val address = sharedPreferences.getString(DOC_PREFS_ADDRESS, null)
        val state = sharedPreferences.getString(DOC_PREFS_STATE, null)
        val district = sharedPreferences.getString(DOC_PREFS_DISTRICT, null)
        val zipCode = sharedPreferences.getString(DOC_PREFS_ZIP_CODE, null)
        val qualification = sharedPreferences.getString(DOC_PREFS_QUALIFICATION, null)
        val specialization = sharedPreferences.getString(DOC_PREFS_SPECIALIZATION, null)
        val medicalRegNo = sharedPreferences.getString(DOC_PREFS_MEDICAL_REG_NO, null)
        val workspaceName = sharedPreferences.getString(DOC_PREFS_WORKSPACE_NAME, null)

        // Convert stored comma-separated string into a List<String> for workingTime
        val workingTimeString = sharedPreferences.getString(DOC_PREFS_WORKING_TIME, null)
        val workingTime = workingTimeString?.split(",") ?: emptyList()

        val experience = sharedPreferences.getInt(DOC_PREFS_EXPERIENCE, 0)
        val fee = sharedPreferences.getInt(DOC_PREFS_FEE, 0)
        val availableForOnlineConsultation =
            sharedPreferences.getBoolean(DOC_PREFS_AVAILABLE_FOR_ONLINE_CONSULTATION, false)

        return if (userFirstName != null && userLastName != null && dob != null && gender != null && phone != null && email != null && token != null && id != null && uid != null) {
            DoctorResponse(
                address ?: "",
                availableForOnlineConsultation,
                district ?: "",
                dob,
                email,
                experience,
                fee,
                userFirstName,
                gender,
                id,
                userLastName,
                medicalRegNo ?: "",
                phone,
                qualification ?: "",
                specialization ?: "",
                state ?: "",
                token,
                uid,
                workingTime,
                workspaceName ?: "",
                zipCode ?: ""
            )
        } else {
            null
        }
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

        return if (userFirstName != null && userLastName != null && age != null && gender != null && bloodGroup != null && phone != null && email != null && token != null && id != null) {
            LoginResponse(
                token, id, userFirstName, userLastName, age, gender, bloodGroup, phone, email
            )
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

    private fun SharedPreferences.Editor.putUUID(key: String, uuid: UUID?) {
        putString(key, uuid?.toString())
    }

    // Extension function to retrieve UUID
    private fun SharedPreferences.getUUID(key: String, default: UUID? = null): UUID? {
        val uuidString = this.getString(key, default?.toString())
        return uuidString?.let { UUID.fromString(it) }
    }
}

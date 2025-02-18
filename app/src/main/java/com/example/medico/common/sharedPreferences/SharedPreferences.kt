package com.example.medico.common.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import com.example.medico.doctor.responses.DoctorLoginResponse
import com.example.medico.user.responses.UserLoginResponse
import com.example.medico.common.navigation.Routes
import com.example.medico.user.responses.UserDetailsResponse
import java.util.UUID

class SharedPreferencesManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val IS_LOGGED_IN_KEY = "IS_LOGGED_IN"
        private const val JWT_TOKEN_KEY = "jwt_token"

        private const val PREFS_EMAIL = "user_email"
        private const val PREFS_ID = "user_id"
        private const val PREFS_PERSONAL_INFO_ID = "personal_info_id"
        private const val PREFS_FIRST_NAME = "user_firstName"
        private const val PREFS_LAST_NAME = "user_lastName"
        private const val PREFS_AGE = "user_age"
        private const val PREFS_GENDER = "user_gender"
        private const val PREFS_BLOOD_GROUP = "user_bloodGroup"
        private const val PREFS_PHONE = "user_phone"
        private const val PREFS_PASSWORD = "user_password"
        private const val PREFS_WEIGHT = "user_weight"
        private const val PREFS_HEIGHT = "user_height"
        private const val PREFS_DOB = "user_dob"
        private const val PREFS_EMERGENCY_CONTACT_NAME = "emergencyContactName"
        private const val PREFS_EMERGENCY_CONTACT_RELATION = "emergencyContactRelation"
        private const val PREFS_EMERGENCY_CONTACT_PHONE = "emergencyContactPhone"
        private const val PREFS_STATE = "user_state"
        private const val PREFS_DISTRICT = "user_district"
        private const val PREFS_CITY = "user_city"
        private const val PREFS_CURRENT_ADDRESS = "currentAddress"
        private const val PREFS_PERMANENT_ADDRESS = "permanentAddress"
        private const val PREFS_KNOWN_HEALTH_CONDITIONS = "knownHealthConditions"
        private const val PREFS_FAMILY_MEDICAL_HISTORY = "familyMedicalHistory"
        private const val PREFS_INSURANCE_PROVIDER = "insuranceProvider"
        private const val PREFS_POLICY_NUMBER = "policyNumber"
        private const val PREFS_GROUP_NUMBER = "groupNumber"
        private const val PREFS_COVERAGE_DETAILS = "coverageDetails"


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
        private const val DOC_PREFS_PASSWORD = "password"

    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getUserDetails(): UserDetailsResponse {
        return UserDetailsResponse(
            id = sharedPreferences.getString(PREFS_PERSONAL_INFO_ID, "").orEmpty(),
            weight = sharedPreferences.getString(PREFS_WEIGHT, "").orEmpty(),
            height = sharedPreferences.getString(PREFS_HEIGHT, "").orEmpty(),
            dob = sharedPreferences.getString(PREFS_DOB, "").orEmpty(),
            emergencyContactName = sharedPreferences.getString(PREFS_EMERGENCY_CONTACT_NAME, "")
                .orEmpty(),
            emergencyContactRelation = sharedPreferences.getString(
                PREFS_EMERGENCY_CONTACT_RELATION,
                ""
            ).orEmpty(),
            emergencyContactPhone = sharedPreferences.getString(PREFS_EMERGENCY_CONTACT_PHONE, "")
                .orEmpty(),
            state = sharedPreferences.getString(PREFS_STATE, "").orEmpty(),
            district = sharedPreferences.getString(PREFS_DISTRICT, "").orEmpty(),
            city = sharedPreferences.getString(PREFS_CITY, "").orEmpty(),
            currentAddress = sharedPreferences.getString(PREFS_CURRENT_ADDRESS, "").orEmpty(),
            permanentAddress = sharedPreferences.getString(PREFS_PERMANENT_ADDRESS, "").orEmpty(),
            knownHealthConditions = sharedPreferences.getString(PREFS_KNOWN_HEALTH_CONDITIONS, "")
                .orEmpty(),
            familyMedicalHistory = sharedPreferences.getString(PREFS_FAMILY_MEDICAL_HISTORY, "")
                .orEmpty(),
            insuranceProvider = sharedPreferences.getString(PREFS_INSURANCE_PROVIDER, "").orEmpty(),
            policyNumber = sharedPreferences.getString(PREFS_POLICY_NUMBER, "").orEmpty(),
            groupNumber = sharedPreferences.getString(PREFS_GROUP_NUMBER, "").orEmpty(),
            coverageDetails = sharedPreferences.getString(PREFS_COVERAGE_DETAILS, "").orEmpty()
        )
    }

    fun saveUserDetails(extraDetails: UserDetailsResponse?) {
        if (extraDetails == null) return // Prevent null pointer exceptions

        with(sharedPreferences.edit()) {
            putIfNotEmpty(PREFS_PERSONAL_INFO_ID, extraDetails.id)
            putIfNotEmpty(PREFS_WEIGHT, extraDetails.weight)
            putIfNotEmpty(PREFS_HEIGHT, extraDetails.height)
            putIfNotEmpty(PREFS_DOB, extraDetails.dob)
            putIfNotEmpty(PREFS_EMERGENCY_CONTACT_NAME, extraDetails.emergencyContactName)
            putIfNotEmpty(PREFS_EMERGENCY_CONTACT_RELATION, extraDetails.emergencyContactRelation)
            putIfNotEmpty(PREFS_EMERGENCY_CONTACT_PHONE, extraDetails.emergencyContactPhone)
            putIfNotEmpty(PREFS_STATE, extraDetails.state)
            putIfNotEmpty(PREFS_DISTRICT, extraDetails.district)
            putIfNotEmpty(PREFS_CITY, extraDetails.city)
            putIfNotEmpty(PREFS_CURRENT_ADDRESS, extraDetails.currentAddress)
            putIfNotEmpty(PREFS_PERMANENT_ADDRESS, extraDetails.permanentAddress)
            putIfNotEmpty(PREFS_KNOWN_HEALTH_CONDITIONS, extraDetails.knownHealthConditions)
            putIfNotEmpty(PREFS_FAMILY_MEDICAL_HISTORY, extraDetails.familyMedicalHistory)
            putIfNotEmpty(PREFS_INSURANCE_PROVIDER, extraDetails.insuranceProvider)
            putIfNotEmpty(PREFS_POLICY_NUMBER, extraDetails.policyNumber)
            putIfNotEmpty(PREFS_GROUP_NUMBER, extraDetails.groupNumber)
            putIfNotEmpty(PREFS_COVERAGE_DETAILS, extraDetails.coverageDetails)

            apply()
        }
    }

    private fun SharedPreferences.Editor.putIfNotEmpty(key: String, value: String) {
        if (value.isNotBlank()) putString(key, value)
    }

    fun saveUserToPreferences(userResponse: UserLoginResponse) {
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
            putString(PREFS_ID, userResponse.id)
            putString(PREFS_PASSWORD, userResponse.password)
            apply()
        }
    }

    fun getUserFromSharedPreferences(): UserLoginResponse? {
        val userFirstName = sharedPreferences.getString(PREFS_FIRST_NAME, null)
        val userLastName = sharedPreferences.getString(PREFS_LAST_NAME, null)
        val age = sharedPreferences.getString(PREFS_AGE, null)
        val gender = sharedPreferences.getString(PREFS_GENDER, null)
        val bloodGroup = sharedPreferences.getString(PREFS_BLOOD_GROUP, null)
        val phone = sharedPreferences.getString(PREFS_PHONE, null)
        val email = sharedPreferences.getString(PREFS_EMAIL, null)
        val token = sharedPreferences.getString(JWT_TOKEN_KEY, null)
        val id = sharedPreferences.getString(PREFS_ID, null)
        val password = sharedPreferences.getString(PREFS_PASSWORD, null)

        return if (userFirstName != null && userLastName != null && age != null && gender != null && bloodGroup != null && phone != null && email != null && token != null && id != null && password != null) {
            UserLoginResponse(
                token,
                id,
                userFirstName,
                userLastName,
                age,
                gender,
                bloodGroup,
                phone,
                email,
                password
            )
        } else {
            null
        }
    }

    fun editUserDetails(age: String, bloodGroup: String, phone: String, email: String) {
        sharedPreferences.edit().apply {
            putString(PREFS_AGE, age)
            putString(PREFS_BLOOD_GROUP, bloodGroup)
            putString(DOC_PREFS_PHONE, phone)
            putString(DOC_PREFS_EMAIL, email)
            apply()
        }
    }

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

    fun saveUserPassword(password: String) {
        sharedPreferences.edit().apply {
            putString(PREFS_PASSWORD, password)
            apply()
        }
    }

    fun saveDocToPreferences(userResponse: DoctorLoginResponse) {
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
            putString(DOC_PREFS_PASSWORD, userResponse.password)
            putInt(DOC_PREFS_EXPERIENCE, userResponse.experience)
            putInt(DOC_PREFS_FEE, userResponse.fee)
            apply()
        }
    }

    fun getDocFromSharedPreferences(): DoctorLoginResponse? {
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
        val password = sharedPreferences.getString(DOC_PREFS_PASSWORD, null)

        val workingTimeString = sharedPreferences.getString(DOC_PREFS_WORKING_TIME, null)
        val workingTime = workingTimeString?.split(",") ?: emptyList()

        val experience = sharedPreferences.getInt(DOC_PREFS_EXPERIENCE, 0)
        val fee = sharedPreferences.getInt(DOC_PREFS_FEE, 0)
        val availableForOnlineConsultation =
            sharedPreferences.getBoolean(DOC_PREFS_AVAILABLE_FOR_ONLINE_CONSULTATION, false)

        return if (userFirstName != null && userLastName != null && dob != null && gender != null && phone != null && email != null && token != null && id != null && uid != null && password != null) {
            DoctorLoginResponse(
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
                zipCode ?: "",
                password
            )
        } else {
            null
        }
    }

    fun editDocPersonalDetails(dob: String, uid: String, phone: String, email: String) {
        sharedPreferences.edit().apply {
            putString(DOC_PREFS_DOB, dob)
            putString(DOC_PREFS_UID, uid)
            putString(DOC_PREFS_PHONE, phone)
            putString(DOC_PREFS_EMAIL, email)
        }
    }

    fun editDocMedicalDetails(
        medicalRegNo: String,
        qualification: String,
        specialization: String,
        experience: Int,
        fee: Int,
        availableForOnlineConsultation: Boolean,
    ) {
        sharedPreferences.edit().apply {
            putString(DOC_PREFS_MEDICAL_REG_NO, medicalRegNo)
            putString(DOC_PREFS_QUALIFICATION, qualification)
            putString(DOC_PREFS_SPECIALIZATION, specialization)
            putInt(DOC_PREFS_EXPERIENCE, experience)
            putInt(DOC_PREFS_FEE, fee)
            putBoolean(DOC_PREFS_AVAILABLE_FOR_ONLINE_CONSULTATION, availableForOnlineConsultation)
        }
    }

    fun editDocAddressDetails(
        workspaceName: String,
        address: String,
        state: String,
        district: String,
        zipCode: String,
    ) {
        sharedPreferences.edit().apply {
            putString(DOC_PREFS_WORKSPACE_NAME, workspaceName)
            putString(DOC_PREFS_ADDRESS, address)
            putString(DOC_PREFS_STATE, state)
            putString(DOC_PREFS_DISTRICT, district)
            putString(DOC_PREFS_ZIP_CODE, zipCode)
        }
    }

    fun getJwtToken(): String? {
        return sharedPreferences.getString(JWT_TOKEN_KEY, null)
    }

    private fun SharedPreferences.Editor.putUUID(key: String, uuid: UUID?) {
        putString(key, uuid?.toString())
    }

    private fun SharedPreferences.getUUID(key: String, default: UUID? = null): UUID? {
        val uuidString = this.getString(key, default?.toString())
        return uuidString?.let { UUID.fromString(it) }
    }

    fun saveUserAddressDetails(
        state: String,
        district: String,
        city: String,
        currentAddress: String,
        permanentAddress: String,
    ) {
        sharedPreferences.edit().apply {
            putString(PREFS_STATE, state)
            putString(PREFS_DISTRICT, district)
            putString(PREFS_CITY, city)
            putString(PREFS_CURRENT_ADDRESS, currentAddress)
            putString(PREFS_PERMANENT_ADDRESS, permanentAddress)
        }
    }
}

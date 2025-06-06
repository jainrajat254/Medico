package com.example.medico.presentation.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.medico.domain.model.EditUserPersonalDetails
import com.example.medico.domain.model.ExtraDetails
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.UserInfoField
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun UserPersonalDetails(
    settingsViewModel: SettingsViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val context = LocalContext.current

    val id = sharedPreferencesManager.getUserProfile()?.id ?: ""
    val firstName = sharedPreferencesManager.getUserProfile()?.firstName ?: ""
    val lastName = sharedPreferencesManager.getUserProfile()?.lastName ?: ""
    val name = "$firstName $lastName"

    var age by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var dob by rememberSaveable { mutableStateOf("") }
    var bloodGroup by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    val extraDetailsState by settingsViewModel.getExtraDetailsState.collectAsState()
    val editDetails by settingsViewModel.editUserDetailsState.collectAsState()

    var isDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(id) {
        settingsViewModel.getExtraDetails(id)
        settingsViewModel.getPersonalInfoId(id)
    }

    LaunchedEffect(Unit) {
        sharedPreferencesManager.getUserProfile()?.let {
            age = it.age
            bloodGroup = it.bloodGroup
            email = it.email
            phone = it.phone
            gender = it.gender
        }
        sharedPreferencesManager.getUserExtraDetails()?.let {
            height = it.height
            weight = it.weight
            dob = it.dob
        }
    }

    // 3. Observe ResultState from extraDetails fetch
    LaunchedEffect(extraDetailsState) {
        when (val result = extraDetailsState) {
            is ResultState.Success -> {
                isDialog = false
                val data = result.data
                height = data.height
                weight = data.weight
                dob = data.dob
            }

            is ResultState.Error -> {
                isDialog = false
                Toast.makeText(
                    context,
                    "Failed to fetch extra details: ${result.error.message ?: "Unknown error"}",
                    Toast.LENGTH_LONG
                ).show()
            }

            is ResultState.Loading -> {
                isDialog = true
            }

            is ResultState.Idle -> isDialog = false
        }
    }

    // 4. Observe editDetails state
    LaunchedEffect(editDetails) {
        when (val result = editDetails) {
            is ResultState.Success -> {
                isDialog = false
                Toast.makeText(context, "Personal Details Updated", Toast.LENGTH_SHORT).show()

                sharedPreferencesManager.getUserProfile()?.let { currentProfile ->
                    sharedPreferencesManager.saveUserProfile(
                        currentProfile.copy(
                            bloodGroup = bloodGroup,
                            phone = phone,
                            email = email,
                            age = age
                        )
                    )
                }

                sharedPreferencesManager.getUserExtraDetails()?.let { currentProfile ->
                    sharedPreferencesManager.saveUserExtraDetails(
                        currentProfile.copy(
                            height = height,
                            weight = weight,
                            dob = dob
                        )
                    )
                }
            }

            is ResultState.Error -> {
                isDialog = false
                Toast.makeText(
                    context,
                    "Error: ${result.error.message ?: "Unknown error"}. Please try again.",
                    Toast.LENGTH_LONG
                ).show()
            }

            ResultState.Loading -> isDialog = true
            ResultState.Idle -> isDialog = false
        }
    }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    UserInfoField("Name", name, onValueChange = {}, enabled = false)
                    UserInfoField("Gender", gender, onValueChange = {}, enabled = false)

                    UserInfoField(
                        "Age",
                        age,
                        onValueChange = { age = it },
                        keyboardType = KeyboardType.Number
                    )
                    UserInfoField("Blood Group", bloodGroup, onValueChange = { bloodGroup = it })
                    UserInfoField(
                        "Height",
                        height,
                        onValueChange = { height = it },
                        keyboardType = KeyboardType.Phone
                    )
                    UserInfoField(
                        "Weight",
                        weight,
                        onValueChange = { weight = it },
                        keyboardType = KeyboardType.Number
                    )
                    UserInfoField("DOB", dob, onValueChange = { dob = it })
                    UserInfoField(
                        "Phone",
                        phone,
                        onValueChange = { phone = it },
                        keyboardType = KeyboardType.Phone
                    )
                    UserInfoField(
                        "Email",
                        email,
                        onValueChange = { email = it },
                        keyboardType = KeyboardType.Email
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val data = EditUserPersonalDetails(age, bloodGroup, phone, email)
                            val extraData =
                                ExtraDetails(height = height, weight = weight, dob = dob)

                            settingsViewModel.editDetails(data, id)
                            settingsViewModel.addExtraDetails(extraData, id)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Details")
                    }
                }
            }
        }
        if (isDialog) {
            CustomLoader()
        }
    }
}

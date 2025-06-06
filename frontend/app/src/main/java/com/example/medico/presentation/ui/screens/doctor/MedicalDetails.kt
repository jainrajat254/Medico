package com.example.medico.presentation.ui.screens.doctor

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.medico.domain.model.EditDocMedicalDetails
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.UserInfoField
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun DocMedicalDetails(
    sharedPreferencesManager: SharedPreferencesManager,
    settingsViewModel: SettingsViewModel,
) {
    val context = LocalContext.current

    var medicalRegNo by remember { mutableStateOf("") }
    var specialization by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var fee by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var availableForOnlineConsultation by remember { mutableStateOf("") }

    var isDialog by remember { mutableStateOf(false) }

    val docId = sharedPreferencesManager.getDocProfile()?.id ?: ""
    val editDocMedicalState by settingsViewModel.editDocMedicalState.collectAsState()

    // ✅ Populate form from sharedPrefs on first load
    LaunchedEffect(Unit) {
        sharedPreferencesManager.getDocProfile()?.let { profile ->
            medicalRegNo = profile.medicalRegNo
            specialization = profile.specialization
            qualification = profile.qualification
            fee = profile.fee.toString()
            experience = profile.experience.toString()
            availableForOnlineConsultation = profile.availableForOnlineConsultation.toString()
        }
    }

    // ✅ Handle update result
    LaunchedEffect(editDocMedicalState) {
        when (val result = editDocMedicalState) {
            is ResultState.Success -> {
                isDialog = false
                Toast.makeText(context, "Medical Details Updated", Toast.LENGTH_SHORT).show()

                sharedPreferencesManager.getDocProfile()?.let { currentProfile ->
                    val updatedProfile = currentProfile.copy(
                        medicalRegNo = medicalRegNo,
                        qualification = qualification,
                        specialization = specialization,
                        experience = experience.toIntOrNull() ?: currentProfile.experience,
                        fee = fee.toIntOrNull() ?: currentProfile.fee,
                        availableForOnlineConsultation = availableForOnlineConsultation.toBooleanStrictOrNull()
                            ?: currentProfile.availableForOnlineConsultation
                    )
                    sharedPreferencesManager.saveDocProfile(updatedProfile) // ✅ SharedPrefs updated here
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    UserInfoField(
                        label = "Medical Registration Number",
                        value = medicalRegNo,
                        onValueChange = { medicalRegNo = it }
                    )
                    UserInfoField(
                        label = "Qualification",
                        value = qualification,
                        onValueChange = { qualification = it }
                    )
                    UserInfoField(
                        label = "Specialization",
                        value = specialization,
                        onValueChange = { specialization = it }
                    )
                    UserInfoField(
                        label = "Experience",
                        value = experience,
                        onValueChange = { experience = it }
                    )
                    UserInfoField(
                        label = "Fee",
                        value = fee,
                        onValueChange = { fee = it }
                    )
                    UserInfoField(
                        label = "Available For Online Consultation",
                        value = availableForOnlineConsultation,
                        onValueChange = { availableForOnlineConsultation = it }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (medicalRegNo.isBlank() || qualification.isBlank() || specialization.isBlank()
                                || experience.isBlank() || fee.isBlank() || availableForOnlineConsultation.isBlank()
                            ) {
                                Toast.makeText(
                                    context,
                                    "Please fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            val data = EditDocMedicalDetails(
                                medicalRegNo = medicalRegNo,
                                qualification = qualification,
                                specialization = specialization,
                                experience = experience.toIntOrNull() ?: 0,
                                fee = fee.toIntOrNull() ?: 0,
                                availableForOnlineConsultation = availableForOnlineConsultation.toBooleanStrictOrNull()
                                    ?: false,
                            )

                            settingsViewModel.editDocMedicalDetails(id = docId, data = data)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors()
                    ) {
                        Text(text = "Save Changes")
                    }
                }
            }
        }
    }
}


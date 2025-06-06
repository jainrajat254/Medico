package com.example.medico.presentation.ui.screens.doctor

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.Medications
import com.example.medico.domain.model.MedicationsDTO
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.CustomTextField
import com.example.medico.presentation.ui.screens.DosageDropdown
import com.example.medico.presentation.ui.screens.DurationDropdown
import com.example.medico.presentation.ui.screens.FrequencyDropdown
import com.example.medico.presentation.ui.screens.IntakeMethodDropdown
import com.example.medico.presentation.ui.screens.MedicationTypeDropdown
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.MedicationsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun AddMedicationPage(
    navController: NavController,
    userDetails: AppointmentDTO? = null,
    existingMedication: MedicationsDTO? = null,
    medicationsViewModel: MedicationsViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val context = LocalContext.current
    val addState by medicationsViewModel.addMedicationState.collectAsState()
    val updateState by medicationsViewModel.updateMedicationState.collectAsState()

    val doctorId = sharedPreferencesManager.getDocProfile()?.id ?: ""
    val doctorFirstName = sharedPreferencesManager.getDocProfile()?.firstName ?: ""
    val doctorLastName = sharedPreferencesManager.getDocProfile()?.lastName ?: ""
    val doctorName = "$doctorFirstName $doctorLastName"

    var medicationName by rememberSaveable {
        mutableStateOf(
            existingMedication?.medicationName ?: ""
        )
    }
    var medicationType by rememberSaveable {
        mutableStateOf(
            existingMedication?.medicationType ?: ""
        )
    }
    var dosageType by rememberSaveable { mutableStateOf(existingMedication?.dosageType ?: "") }
    var intakeMethod by rememberSaveable { mutableStateOf(existingMedication?.intakeMethod ?: "") }
    var frequency by rememberSaveable { mutableStateOf(existingMedication?.frequency ?: "") }
    var duration by rememberSaveable { mutableStateOf(existingMedication?.duration ?: "") }
    var time by rememberSaveable { mutableStateOf(existingMedication?.time ?: "") }

    val isUpdating = existingMedication != null

    var isDialog by remember {
        mutableStateOf(false)
    }

    // Handle success navigation or toast
    LaunchedEffect(addState, updateState) {
        val state = if (isUpdating) updateState else addState
        when (state) {
            is ResultState.Success -> {
                isDialog = false
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }

            is ResultState.Error -> {
                isDialog = false
                Toast.makeText(context, "Error: ${state.error.message}", Toast.LENGTH_LONG).show()
            }

            is ResultState.Loading -> isDialog = true
            is ResultState.Idle -> isDialog = false
        }
    }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Text(
                        text = if (isUpdating) "Update Medication" else "Add Medications",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }

                item {
                    CustomTextField(
                        value = medicationName,
                        onValueChange = { medicationName = it },
                        label = "Medication Name",
                        enabled = !isUpdating
                    )
                }

                item {
                    MedicationTypeDropdown(
                        selectedType = medicationType,
                        onTypeSelected = { medicationType = it },
                        modifier = Modifier
                    )
                }

                item {
                    DosageDropdown(
                        selectedType = medicationType,
                        selectedDosage = dosageType,
                        onDosageSelected = { dosageType = it },
                        modifier = Modifier
                    )
                }

                item {
                    IntakeMethodDropdown(
                        selectedMethod = intakeMethod,
                        onMethodSelected = { intakeMethod = it },
                        modifier = Modifier
                    )
                }

                item {
                    FrequencyDropdown(
                        selectedFrequency = frequency,
                        onFrequencySelected = { frequency = it },
                        modifier = Modifier
                    )
                }

                item {
                    DurationDropdown(
                        selectedDuration = duration,
                        onDurationSelected = { duration = it },
                        modifier = Modifier
                    )
                }

                item {
                    CustomTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = "Time (e.g., 10:30 AM)"
                    )
                }

                item {
                    Button(
                        onClick = {
                            val medication = Medications(
                                medicationName = medicationName,
                                medicationType = medicationType,
                                dosageType = dosageType,
                                intakeMethod = intakeMethod,
                                frequency = frequency,
                                duration = duration,
                                time = time,
                                doctorId = doctorId,
                                doctorName = doctorName
                            )

                            if (isUpdating) {
                                medicationsViewModel.updateMedication(
                                    existingMedication!!.id,
                                    medication
                                )
                            } else {
                                medicationsViewModel.addMedications(
                                    medication,
                                    userDetails?.userId ?: ""
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(50.dp)
                    ) {
                        Text(
                            text = if (isUpdating) "UPDATE" else "ADD",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        if (isDialog) {
            CustomLoader()
        }
    }
}



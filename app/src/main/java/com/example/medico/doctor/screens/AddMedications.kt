package com.example.medico.doctor.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.CustomTextField
import com.example.medico.common.utils.DosageDropdown
import com.example.medico.common.utils.DurationDropdown
import com.example.medico.common.utils.FrequencyDropdown
import com.example.medico.common.utils.IntakeMethodDropdown
import com.example.medico.common.utils.MedicationTypeDropdown
import com.example.medico.doctor.viewModel.AddMedications
import com.example.medico.user.dto.AppointmentDTO
import com.example.medico.user.dto.MedicationsDTO
import org.koin.androidx.compose.getViewModel

@Composable
fun AddMedicationPage(
    navController: NavController,
    userDetails: AppointmentDTO? = null,
    existingMedication: MedicationsDTO? = null,
) {
    val viewModel: AddMedications = getViewModel()
    val medicationState by viewModel.medicationState.collectAsState()

    LaunchedEffect(existingMedication) {
        existingMedication?.let { medication ->
            viewModel.updateMedicationState(
                medicationName = medication.medicationName,
                medicationType = medication.medicationType,
                dosageType = medication.dosageType,
                intakeMethod = medication.intakeMethod,
                frequency = medication.frequency,
                duration = medication.duration,
                time = medication.time
            )
        }
    }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Text(
                text = if (existingMedication == null) "Add Medications" else "Update Medication",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                // Medication Name (Disabled when updating)
                item {
                    CustomTextField(
                        value = medicationState.medicationName,  // Use ViewModel state directly
                        onValueChange = {
                            if (existingMedication == null) {  // Allow changes only for new medications
                                viewModel.updateMedicationState(medicationName = it)
                            }
                        },
                        label = "Medication Name",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        enabled = existingMedication == null,  // Disabled only for updates
                    )

                }

                item {
                    MedicationTypeDropdown(
                        selectedType = medicationState.medicationType,
                        onTypeSelected = { viewModel.updateMedicationState(medicationType = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }

                item {
                    DosageDropdown(
                        selectedType = medicationState.medicationType,
                        selectedDosage = medicationState.dosageType,
                        onDosageSelected = { viewModel.updateMedicationState(dosageType = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }

                item {
                    IntakeMethodDropdown(
                        selectedMethod = medicationState.intakeMethod,
                        onMethodSelected = { viewModel.updateMedicationState(intakeMethod = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }

                item {
                    FrequencyDropdown(
                        selectedFrequency = medicationState.frequency,
                        onFrequencySelected = { viewModel.updateMedicationState(frequency = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }

                item {
                    DurationDropdown(
                        selectedDuration = medicationState.duration,
                        onDurationSelected = { viewModel.updateMedicationState(duration = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }

                item {
                    CustomTextField(
                        value = medicationState.time,
                        onValueChange = { viewModel.updateMedicationState(time = it) },
                        label = "Time (e.g., 10:30 AM)",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }

                // Button - Different action for Add and Update
                item {
                    Button(
                        onClick = {
                            if (existingMedication == null) {
                                // Add New Medication
                                viewModel.addMedications(
                                    id = userDetails?.userId ?: "",
                                    onSuccess = { navController.popBackStack() },
                                    onError = { Log.d("Medications", it) }
                                )
                            } else {
                                // Update Existing Medication
                                viewModel.updateMedication(
                                    medId = existingMedication.id,
                                    onSuccess = { navController.popBackStack() },
                                    onError = { Log.d("Medications", it) }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(50.dp)
                    ) {
                        Text(
                            text = if (existingMedication == null) "ADD" else "UPDATE",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

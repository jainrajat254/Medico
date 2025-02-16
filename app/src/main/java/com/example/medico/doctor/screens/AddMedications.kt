package com.example.medico.doctor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.CustomTextField
import com.example.medico.common.utils.DosageDropdown
import com.example.medico.common.utils.DurationDropdown
import com.example.medico.common.utils.FrequencyDropdown
import com.example.medico.common.utils.IntakeMethodDropdown
import com.example.medico.common.utils.MedicationTypeDropdown
import com.example.medico.doctor.model.AddMedications

@Preview(showBackground = true)
@Composable
fun AddMedicationPage() {
    val viewModel: AddMedications = viewModel()

    val medication by viewModel.medication.collectAsState()
    val dosage by viewModel.dosage.collectAsState()
    val time by viewModel.time.collectAsState()
    val frequency by viewModel.frequency.collectAsState()
    val medicationType by viewModel.medicationType.collectAsState()
    val dosageType by viewModel.dosageType.collectAsState()
    val duration by viewModel.duration.collectAsState()
    val intakeMethod by viewModel.intakeMethod.collectAsState()

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Text(
                text = "Add Medications",
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    CustomTextField(
                        value = medication,
                        onValueChange = { viewModel.setMedication(it) },
                        label = "Medication Name",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Matching height
                    )
                }

                item {
                    CustomTextField(
                        value = time,
                        onValueChange = { viewModel.setTime(it) },
                        label = "Time (e.g., 10:30 AM)",
                        keyboardType = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Matching height
                    )
                }

                item {
                    MedicationTypeDropdown(
                        selectedType = medicationType,
                        onTypeSelected = viewModel::setMedicationType,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Matching height
                    )
                }

                item {
                    DosageDropdown(
                        selectedDosage = dosageType,
                        onDosageSelected = viewModel::setDosageType,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Matching height
                    )
                }

                item {
                    FrequencyDropdown(
                        selectedFrequency = frequency,
                        onFrequencySelected = viewModel::setFrequency,
                    )
                }

                item {
                    DurationDropdown(
                        selectedDuration = duration,
                        onDurationSelected = viewModel::setDuration,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Matching height
                    )
                }

                item {
                    IntakeMethodDropdown(
                        selectedMethod = intakeMethod,
                        onMethodSelected = viewModel::setIntakeMethod,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Matching height
                    )
                }

                item {
                    Button(
                        onClick = { /* Handle Add Medication Logic */ },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "ADD",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}



package com.example.medico.doctor.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.medico.R
import com.example.medico.common.navigation.BottomNavBar
import com.example.medico.common.model.Frequency
import com.example.medico.common.utils.CustomTextField
import com.example.medico.common.utils.HeaderSection
import com.example.medico.common.utils.Tagline
import com.example.medico.doctor.model.AddMedications

@Composable
fun AddMedicationPage(navController: NavHostController) {
    val viewModel: AddMedications = viewModel()

    Scaffold(
        bottomBar = { BottomNavBar(modifier = Modifier, navController = navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.background_app),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            HeaderSection()

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Tagline()

                Text(
                    text = "Add Medications",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(60.dp))

                CustomTextField(
                    value = viewModel.medication.value,
                    onValueChange = { viewModel.medication.value = it },
                    label = "Medication Name",
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    value = viewModel.dosage.value,
                    onValueChange = { viewModel.dosage.value = it },
                    label = "Dosage",
                    keyboardType = KeyboardType.Text,
                    
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    value = viewModel.time.value,
                    onValueChange = { viewModel.time.value = it },
                    label = "Time",
                    keyboardType = KeyboardType.Number,
                    
                )

                Spacer(modifier = Modifier.height(12.dp))

                FrequencyDropdown(viewModel = viewModel)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
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

@Composable
fun FrequencyDropdown(viewModel: AddMedications) {
    Box(modifier = Modifier.fillMaxWidth()) {
        CustomTextField(
            value = viewModel.frequency.value?.fre ?: "",
            onValueChange = { },
            label = "Frequency",
            modifier = Modifier.padding(start = 32.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { viewModel.frequencyExpanded.value = !viewModel.frequencyExpanded.value }) {
                    Icon(
                        imageVector = if (viewModel.frequencyExpanded.value) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            
        )

        DropdownMenu(
            expanded = viewModel.frequencyExpanded.value,
            onDismissRequest = { viewModel.frequencyExpanded.value = false },
            modifier = Modifier.fillMaxWidth() // Ensures the dropdown menu also takes full width
        ) {
            Frequency.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.fre) },
                    onClick = {
                        viewModel.frequency.value = option
                        viewModel.frequencyExpanded.value = false
                    }
                )
            }
        }
    }
}


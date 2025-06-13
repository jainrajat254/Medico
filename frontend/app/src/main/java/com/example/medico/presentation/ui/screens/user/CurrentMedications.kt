package com.example.medico.presentation.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medico.domain.model.MedicationResponse
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.MedicationCardUser
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.MedicationsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun CurrentMedications(
    medicationsViewModel: MedicationsViewModel,
) {

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Current Medications",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                CurrentMedicationList(medicationsViewModel)
            }
        }
    }
}

@Composable
fun CurrentMedicationList(
    medicationsViewModel: MedicationsViewModel,
) {
    val medicationsState by medicationsViewModel.getMedicationsState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top
    ) {
        when (medicationsState) {
            is ResultState.Loading -> {
                CustomLoader()
            }

            is ResultState.Success -> {
                val medications =
                    (medicationsState as ResultState.Success<List<MedicationResponse>>).data
                if (medications.isEmpty()) {
                    NotAvailable(label = "No medication to show")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(medications) { medication ->
                            MedicationCardUser(medication)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            is ResultState.Error -> {
                val errorMessage = (medicationsState as ResultState.Error).error.message
                NotAvailable(label = "Error loading medications: $errorMessage")
            }

            ResultState.Idle -> {

            }
        }
    }
}

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.medico.presentation.ui.navigation.UserBottomNavBar
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.ui.screens.OldMedicationCard
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.MedicationsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun MedicationPage(
    navController: NavHostController,
    sharedPreferencesManager: SharedPreferencesManager,
    medicationsViewModel: MedicationsViewModel,
) {
    Scaffold(
        bottomBar = { UserBottomNavBar(navController = navController, modifier = Modifier) }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Past Medications",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                OldMedicationList(sharedPreferencesManager, medicationsViewModel)
            }
        }
    }
}

@Composable
fun OldMedicationList(
    sharedPreferencesManager: SharedPreferencesManager,
    medicationsViewModel: MedicationsViewModel,
) {
    val context = LocalContext.current
    val id = sharedPreferencesManager.getUserProfile()?.id ?: ""
    val oldMedicationsState by medicationsViewModel.oldMedicationsState.collectAsState()

    // Fetch medications
    LaunchedEffect(id) {
        medicationsViewModel.oldMedications(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        when (val result = oldMedicationsState) {
            is ResultState.Loading -> {
                CustomLoader() // Or use CircularProgressIndicator()
            }

            is ResultState.Error -> {
                NotAvailable(label = "Error: ${result.error.message ?: "Unable to load medications"}")
            }

            is ResultState.Success -> {
                val medications = result.data
                if (medications.isEmpty()) {
                    NotAvailable(label = "No medication to show")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(medications) { medication ->
                            OldMedicationCard(medication)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            ResultState.Idle -> {
                // No-op or placeholder
            }
        }
    }
}


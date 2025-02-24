package com.example.medico.user.screens

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
import androidx.navigation.NavHostController
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.MedicationCardUser
import com.example.medico.common.utils.NotAvailable
import com.example.medico.common.viewModel.AuthViewModel

@Composable
fun CurrentMedications(
    sharedPreferencesManager: SharedPreferencesManager,
    vm: AuthViewModel,
    navController: NavHostController
) {

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                CurrentMedicationList(sharedPreferencesManager, vm)
            }
        }
    }
}

@Composable
fun CurrentMedicationList(sharedPreferencesManager: SharedPreferencesManager, vm: AuthViewModel) {
    val id = sharedPreferencesManager.getUserId()
    val medications by vm.medications.collectAsState()

    LaunchedEffect(id) {
        vm.getMedication(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        if (medications.isEmpty()) {
            NotAvailable(label = "No medication to show")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(medications) { medication ->
                    MedicationCardUser(medication)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
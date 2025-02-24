package com.example.medico.doctor.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.MedicationCardUser
import com.example.medico.common.utils.NotAvailable
import com.example.medico.common.utils.OldMedicationCard
import com.example.medico.common.viewModel.AuthViewModel

@Composable
fun AllMedications(userId: String, vm: AuthViewModel, navController: NavController) {
    val medications by remember { vm.medications }.collectAsState()
    val oldMedications by remember { vm.oldMedications }.collectAsState()

    LaunchedEffect(userId) {
        vm.getMedication(id = userId)
        vm.oldMedications(userId = userId)
    }

    if (medications.isEmpty() && oldMedications.isEmpty()) {
        NotAvailable(label = "No Medications Available")
    }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn {
                items(medications) { medication ->
                    MedicationCardUser(
                        medication = medication,
                        showActions = true,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(oldMedications) { medication ->
                    OldMedicationCard(
                        medication = medication,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}
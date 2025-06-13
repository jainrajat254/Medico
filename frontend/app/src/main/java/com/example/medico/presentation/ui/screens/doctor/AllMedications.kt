package com.example.medico.presentation.ui.screens.doctor

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.MedicationCardUser
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.ui.screens.OldMedicationCard
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.MedicationsViewModel
import com.example.medico.utils.ResultState

@Composable
fun AllMedications(
    userId: String,
    medicationsViewModel: MedicationsViewModel,
) {
    val getMedicationsState by medicationsViewModel.getMedicationsState.collectAsState()
    val oldMedicationsState by medicationsViewModel.oldMedicationsState.collectAsState()
    val context = LocalContext.current

    var hasLoadedOnce by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasLoadedOnce) {
            medicationsViewModel.loadAllMedicationsForCurrentUser(userId = userId)
            hasLoadedOnce = true
        }
    }
    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            when {
                getMedicationsState is ResultState.Loading || oldMedicationsState is ResultState.Loading -> {
                    CustomLoader()
                }

                getMedicationsState is ResultState.Error || oldMedicationsState is ResultState.Error -> {
                    val errorMessage = listOfNotNull(
                        (getMedicationsState as? ResultState.Error)?.error?.message.toString(),
                        (oldMedicationsState as? ResultState.Error)?.error?.message.toString()
                    ).joinToString("\n")
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

                getMedicationsState is ResultState.Success && oldMedicationsState is ResultState.Success -> {
                    val medications = (getMedicationsState as ResultState.Success).data
                    val oldMedications = (oldMedicationsState as ResultState.Success).data

                    if (medications.isEmpty() && oldMedications.isEmpty()) {
                        NotAvailable(label = "No Medications Available")
                    } else {
                        LazyColumn {
                            items(medications) { medication ->
                                MedicationCardUser(
                                    medication = medication,
                                    showActions = true,
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            items(oldMedications) { medication ->
                                OldMedicationCard(medication = medication)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }

                else -> {
                    NotAvailable(label = "No Medications Available")
                }
            }
        }
    }
}


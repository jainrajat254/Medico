package com.example.medico.presentation.ui.screens.doctor

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.medico.R
import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.MedicationsDTO
import com.example.medico.presentation.ui.navigation.DocBottomNavBar
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.presentation.ui.screens.user.RecordCard
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.CurrentPatientCard
import com.example.medico.presentation.ui.screens.MedicationCardDoc
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.ui.screens.ReportCard
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.MedicationsViewModel
import com.example.medico.presentation.viewmodel.RecordsViewModel
import com.example.medico.presentation.viewmodel.ReportsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun CurrentPatientInfo(
    userDetails: AppointmentDTO,
    index: Int,
    reportsViewModel: ReportsViewModel,
    recordsViewModel: RecordsViewModel,
    medicationsViewModel: MedicationsViewModel,
    navController: NavHostController,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val context = LocalContext.current
    val docId = sharedPreferencesManager.getDocProfile()?.id.orEmpty()

    var showDialog by remember { mutableStateOf(false) }
    var selectedMedication by remember { mutableStateOf<MedicationsDTO?>(null) }

    val medicationsState by medicationsViewModel.doctorMedicationsState.collectAsState()
    val recordsState by recordsViewModel.getRecordsState.collectAsState()
    val reportsState by reportsViewModel.getReportsState.collectAsState()
    val isRemovingMedication by medicationsViewModel.removeMedicationState.collectAsState()

    LaunchedEffect(Unit) {
        medicationsViewModel.doctorMedication(docId, userDetails.userId)
        recordsViewModel.getRecords(userDetails.userId)
        reportsViewModel.getReports(userDetails.userId)
    }

    Scaffold(
        floatingActionButton = { FloatingActionButtonWithDropdown(navController, userDetails) },
        bottomBar = { DocBottomNavBar(modifier = Modifier, navController = navController) }
    ) { paddingValues ->

        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    CurrentPatientCard(
                        patientName = userDetails.patientName,
                        index = index,
                        showPersonalInfoOnly = true,
                        onPersonalInfoClick = {
                            navController.navigate(Routes.UserOverview.createRoutes(userDetails.userId))
                        }
                    )
                }

                // ----------------- Medications -----------------
                item { SectionHeader("Ongoing Medications") }

                when (medicationsState) {
                    is ResultState.Loading -> item { CustomLoader() }
                    is ResultState.Error -> item {
                        Toast.makeText(
                            context,
                            (medicationsState as ResultState.Error).error.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is ResultState.Success -> {
                        val meds = (medicationsState as ResultState.Success).data
                        if (meds.isEmpty()) {
                            item { NotAvailable(label = "No Medications Available") }
                        } else {
                            items(meds) { medication ->
                                MedicationCardDoc(
                                    medication = medication,
                                    showActions = true,
                                    onUpdateClick = {
                                        navController.navigate(
                                            Routes.UpdateMed.createRoute(
                                                medication
                                            )
                                        )
                                    },
                                    onRemoveClick = {
                                        selectedMedication = medication
                                        showDialog = true
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }

                    ResultState.Idle -> Unit
                }

                item {
                    CenteredButton("Show all medications") {
                        navController.navigate(Routes.AllMedications.createRoutes(userDetails.userId))
                    }
                }

                // ----------------- Records -----------------
                item { SectionHeader("Records") }

                when (recordsState) {
                    is ResultState.Loading -> item { CustomLoader() }
                    is ResultState.Error -> item {
                        Toast.makeText(
                            context,
                            (recordsState as ResultState.Error).error.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is ResultState.Success -> {
                        val records = (recordsState as ResultState.Success).data
                        if (records.isEmpty()) {
                            item { NotAvailable(label = "No Records Available") }
                        } else {
                            items(records) { record ->
                                RecordCard(
                                    record = record,
                                    showExportButton = false,
                                    onViewFullRecordClick = {
                                        recordsViewModel.downloadAndOpenPdf(record.id, context)
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }

                    ResultState.Idle -> Unit
                }

                // ----------------- Reports -----------------
                item { SectionHeader("Reports") }

                when (reportsState) {
                    is ResultState.Loading -> item { CustomLoader() }
                    is ResultState.Error -> item {
                        Toast.makeText(
                            context,
                            (reportsState as ResultState.Error).error.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is ResultState.Success -> {
                        val reports = (reportsState as ResultState.Success).data
                        if (reports.isEmpty()) {
                            item { NotAvailable(label = "No Reports Available") }
                        } else {
                            items(reports) { report ->
                                ReportCard(
                                    report = report,
                                    showExportButton = false,
                                    onViewFullReportClick = {
                                        reportsViewModel.downloadAndOpenPdf(report.id, context)
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }

                    ResultState.Idle -> Unit
                }
            }

            // ----------------- Remove Medication Dialog -----------------
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirmation") },
                    text = { Text("Are you sure you want to remove ${selectedMedication?.medicationName}?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                selectedMedication?.let { medication ->
                                    medicationsViewModel.removeMedications(medication.id)
                                    medicationsViewModel.doctorMedication(docId, userDetails.userId)
                                    showDialog = false
                                }
                            },
                        ) {
                            if (isRemovingMedication is ResultState.Loading) {
                                CustomLoader()
                            } else {
                                Text("OK")
                            }
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 12.dp, start = 8.dp),
        color = Color.Black
    )
}

@Composable
fun FloatingActionButtonWithDropdown(navController: NavController, userDetails: AppointmentDTO) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(horizontalAlignment = Alignment.End) {

            // ✅ Always include DropdownMenu
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false },
                modifier = Modifier
                    .width(200.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                val typeOptions = mapOf(
                    "Add Medication" to Routes.MedAdd.createRoutes(userDetails = userDetails),
                    "Add Report" to Routes.AddReport.createRoutes(userDetails = userDetails),
                    "Add Record" to Routes.AddRecord.createRoutes(userDetails = userDetails)
                )

                typeOptions.forEach { (label, route) ->
                    DropdownMenuItem(
                        text = { Text(label, fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                        onClick = {
                            isDropdownExpanded = false
                            navController.navigate(route)
                        }
                    )
                }
            }

            FloatingActionButton(
                onClick = { isDropdownExpanded = !isDropdownExpanded },
                containerColor = Color(0xFF4771CC),
                contentColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "add",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun CenteredButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onClick) {
            Text(text)
        }
    }
}

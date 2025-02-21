package com.example.medico.doctor.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.medico.R
import com.example.medico.common.navigation.DocBottomNavBar
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.*
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.dto.AppointmentDTO
import com.example.medico.user.dto.MedicationsDTO

@Composable
fun CurrentPatientInfo(
    userDetails: AppointmentDTO,
    index: Int,
    vm: AuthViewModel,
    navController: NavHostController,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val medications by vm.docMedications.collectAsState()
    val reports by vm.reports.collectAsState()
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var selectedMedication by remember { mutableStateOf<MedicationsDTO?>(null) }

    LaunchedEffect(sharedPreferencesManager.getDocId()) {
        vm.doctorMedication(sharedPreferencesManager.getDocId(), userDetails.userId)
        vm.getReports(userDetails.userId)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButtonWithDropdown(navController, userDetails)
        },
        bottomBar = { DocBottomNavBar(modifier = Modifier, navController = navController) }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    CurrentPatientCard(
                        patientName = userDetails.patientName,
                        index = index,
                        showPersonalInfoOnly = true,
                        onPersonalInfoClick = {
                            navController.navigate(Routes.UserOverview.createRoutes(userDetails.userId)) {
                                launchSingleTop = true
                            }
                        }
                    )
                }

                // Medications Section
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Ongoing Medications",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 12.dp, start = 8.dp),
                        color = Color.Black
                    )
                }

                if (medications.isEmpty()) {
                    item {
                        NotAvailable(label = "No Medications Available")
                    }
                } else {
                    items(medications) { medication ->
                        MedicationCardDoc(
                            medication = medication,
                            showActions = true,
                            onUpdateClick = {
                                navController.navigate(Routes.UpdateMed.createRoute(medication))
                            },
                            onRemoveClick = {
                                selectedMedication = medication
                                showDialog = true
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Reports Section
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Reports",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 12.dp, start = 8.dp),
                        color = Color.Black
                    )
                }

                if (reports.isEmpty()) {
                    item {
                        NotAvailable(label = "No Reports Available")
                    }
                } else {
                    items(reports) { report ->
                        ReportCard(
                            report = report,
                            showExportButton = false,
                            onViewFullReportClick = { vm.downloadAndOpenPdf(report.id, context) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirmation") },
                    text = { Text("Are you sure you want to remove ${selectedMedication?.medicationName}?") },
                    confirmButton = {
                        Button(onClick = {
                            showDialog = false
                        }) {
                            Text("OK")
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
fun FloatingActionButtonWithDropdown(navController: NavController, userDetails: AppointmentDTO) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(horizontalAlignment = Alignment.End) {
            if (isDropdownExpanded) {
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier
                        .width(200.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val typeOptions = mapOf(
                        "Add Medication" to Routes.MedAdd.createRoutes(userDetails = userDetails),
                        "Add Report" to Routes.AddReport.createRoutes(userDetails = userDetails)
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
            }

            FloatingActionButton(
                onClick = { isDropdownExpanded = !isDropdownExpanded },
                containerColor = Color(0xFF4771CC),
                contentColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(end = 16.dp)
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

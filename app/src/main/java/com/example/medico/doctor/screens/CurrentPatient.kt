package com.example.medico.doctor.screens

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
import com.example.medico.common.navigation.DocBottomNavBar
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.CurrentPatientCard
import com.example.medico.common.utils.MedicationCardDoc
import com.example.medico.common.utils.NotAvailable
import com.example.medico.common.utils.ReportCard
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.dto.AppointmentDTO

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

    LaunchedEffect(sharedPreferencesManager.getDocId()) {
        vm.doctorMedication(sharedPreferencesManager.getDocId(), userDetails.userId)
        vm.getReports(userDetails.userId)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButtonWithDropdown(navController,userDetails)
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
                        },

                        )
                }
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
                            onRemoveClick = { /* Handle removal */ }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
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
        }
    }
}

@Composable
fun FloatingActionButtonWithDropdown(navController: NavController, userDetails: AppointmentDTO) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp), // Adjust for bottom navbar spacing
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
                                selectedType = label
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



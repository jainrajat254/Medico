package com.example.medico.doctor.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.common.navigation.DocBottomNavBar
import com.example.medico.common.navigation.Routes
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.CurrentPatientCard
import com.example.medico.common.utils.MedicationCard
import com.example.medico.common.utils.ReportCard
import com.example.medico.user.screens.medications
import com.example.medico.user.screens.reports

@Composable
fun CurrentPatientInfo(
    navController: NavController,
) {
    Scaffold(
        bottomBar = { DocBottomNavBar(modifier = Modifier, navController = navController) }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    CurrentPatientCard(
                        patientName = "John Doe",
                        index = 1,
                        appointmentTime = "10:30 AM",
                        showPersonalInfoOnly = true,
                        onPersonalInfoClick = { navController.navigate(Routes.CurrentPatientDetails.routes) }
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
                items(medications) { medication ->
                    MedicationCard(
                        medication = medication,
                        showActions = true,
                        onUpdateClick = { /* Handle update */ },
                        onRemoveClick = { /* Handle removal */ }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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
                items(reports) { report ->
                    ReportCard(
                        report = report,
                        showExportButton = false,
                        onViewFullReportClick = { }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

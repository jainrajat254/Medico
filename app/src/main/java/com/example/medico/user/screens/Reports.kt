package com.example.medico.user.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.medico.common.model.HealthReport
import com.example.medico.common.navigation.UserBottomNavBar
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.ReportCard

val reports = listOf(
    HealthReport("Blood Sugar(HbA1c", "Dr. Santosh Shukla", "Needs Attention !"),
    HealthReport("Thyroid Profile", "Dr. B.N Ghosh", "Normal"),
    HealthReport("Lipid Profile", "Dr. Raghav Sharma", "Needs Attention !"),
    HealthReport("Platelets", "Dr. Raghav Sharma", "Needs Attention !")
)

@Composable
fun HealthReports(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            UserBottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {

            // Medical Reports Title
            Text(
                text = "Medical Reports",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(reports) { report ->
                    ReportCard(
                        report = report,
                        showExportButton = true,
                        onExportClick = { /* Handle Export */ },
                        onViewFullReportClick = { /* Handle View Report */ }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


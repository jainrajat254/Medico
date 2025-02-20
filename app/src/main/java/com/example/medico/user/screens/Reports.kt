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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.medico.common.navigation.UserBottomNavBar
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.NotAvailable
import com.example.medico.common.utils.ReportCard
import com.example.medico.common.viewModel.AuthViewModel

@Composable
fun HealthReports(
    navController: NavHostController,
    sharedPreferencesManager: SharedPreferencesManager,
    vm: AuthViewModel
) {
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
            HealthReportsList(sharedPreferencesManager = sharedPreferencesManager, vm = vm)
        }
    }
}

@Composable
fun HealthReportsList(sharedPreferencesManager: SharedPreferencesManager, vm: AuthViewModel) {
    val id = sharedPreferencesManager.getUserId()
    val reports by vm.reports.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(id) {
        vm.getReports(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        if (reports.isEmpty()) {
            NotAvailable(label = "No Reports Available")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(reports) { report ->
                    ReportCard(
                        report = report,
                        showExportButton = true,
                        onExportClick = { vm.exportPdf(report.id, context) },
                        onViewFullReportClick = { vm.downloadAndOpenPdf(report.id,context) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


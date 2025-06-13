package com.example.medico.presentation.ui.screens.user

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.medico.domain.model.ReportsResponse
import com.example.medico.presentation.ui.navigation.UserBottomNavBar
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.ui.screens.ReportCard
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.ReportsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun HealthReports(
    navController: NavHostController,
    sharedPreferencesManager: SharedPreferencesManager,
    reportsViewModel: ReportsViewModel,
) {
    Scaffold(
        bottomBar = {
            UserBottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {

            Text(
                text = "Medical Reports",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            HealthReportsList(
                sharedPreferencesManager = sharedPreferencesManager,
                reportsViewModel = reportsViewModel
            )
        }
    }
}

@Composable
fun HealthReportsList(
    sharedPreferencesManager: SharedPreferencesManager,
    reportsViewModel: ReportsViewModel
) {
    val userId = sharedPreferencesManager.getUserProfile()?.id.orEmpty()
    val getReportsState by reportsViewModel.getReportsState.collectAsState()
    val context = LocalContext.current

    var hasLoadedOnce by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasLoadedOnce) {
            reportsViewModel.loadReportsForCurrentUser(userId = userId)
            hasLoadedOnce = true
        }
    }

    when (getReportsState) {
        is ResultState.Loading -> {
            CustomLoader()
        }

        is ResultState.Success -> {
            val reports = (getReportsState as ResultState.Success<List<ReportsResponse>>).data

            if (reports.isEmpty()) {
                NotAvailable(label = "No Reports Available")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items(reports) { report ->
                        ReportCard(
                            report = report,
                            showExportButton = true,
                            onExportClick = {
                                reportsViewModel.exportPdf(reportId = report.id, context = context)
                            },
                            onViewFullReportClick = {
                                reportsViewModel.downloadAndOpenPdf(reportId = report.id, context = context)
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

        is ResultState.Error -> {
            val error = (getReportsState as ResultState.Error).error.message
            NotAvailable(label = "Error loading reports: $error")
        }

        ResultState.Idle -> {
            // Optional placeholder or empty state
        }
    }
}
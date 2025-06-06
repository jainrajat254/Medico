package com.example.medico.presentation.ui.screens.user

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.domain.model.RecordsResponse
import com.example.medico.presentation.ui.navigation.UserBottomNavBar
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun Records(
    navController: NavController,
    sharedPreferencesManager: SharedPreferencesManager,
    recordsViewModel: com.example.medico.presentation.viewmodel.RecordsViewModel,
) {
    Scaffold(
        bottomBar = { UserBottomNavBar(modifier = Modifier, navController = navController) }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Text(
                text = "Medical Records",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            RecordsList(
                sharedPreferencesManager = sharedPreferencesManager,
                recordsViewModel = recordsViewModel
            )
        }
    }
}

@Composable
fun RecordsList(
    sharedPreferencesManager: SharedPreferencesManager,
    recordsViewModel: com.example.medico.presentation.viewmodel.RecordsViewModel,
) {
    val userId = sharedPreferencesManager.getUserProfile()?.id ?: ""
    val getRecordsState by recordsViewModel.getRecordsState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(userId) {
        if (userId.isNotBlank()) {
            recordsViewModel.getRecords(userId)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        when (getRecordsState) {
            is ResultState.Loading -> {
                CustomLoader()
            }

            is ResultState.Success -> {
                val records = (getRecordsState as ResultState.Success<List<RecordsResponse>>).data
                if (records.isEmpty()) {
                    NotAvailable(label = "No Records Available")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(records) { record ->
                            RecordCard(
                                record = record,
                                showExportButton = true,
                                onExportClick = {
                                    recordsViewModel.exportPdf(
                                        recordId = record.id,
                                        context = context
                                    )
                                },
                                onViewFullRecordClick = {
                                    recordsViewModel.downloadAndOpenPdf(
                                        recordId = record.id,
                                        context = context
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            is ResultState.Error -> {
                val errorMessage = (getRecordsState as ResultState.Error).error.message
                NotAvailable(label = "Error loading records: $errorMessage")
            }

            ResultState.Idle -> {
                // Optional placeholder
            }
        }
    }
}


@Composable
fun RecordCard(
    record: RecordsResponse,
    showExportButton: Boolean = false,
    onExportClick: (() -> Unit)? = null,
    onViewFullRecordClick: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .border(2.dp, Color(0xFF4771CC), RoundedCornerShape(12.dp))
            .shadow(6.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {  // Increased padding
            Text(
                text = record.recordName,
                fontSize = 20.sp,  // Slightly larger title
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4771CC)
            )

            HorizontalDivider(
                color = Color(0xFFCCCCCC),
                thickness = 1.2.dp,  // Slightly thicker divider
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "Reviewed by: Dr. ${record.reviewedBy}",
                fontSize = 15.sp,  // Increased size for better readability
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Review: ${record.review}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Date: ${record.date}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showExportButton) {
                    Button(
                        onClick = { onExportClick?.invoke() },
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp)  // Slightly taller button
                            .shadow(6.dp, RoundedCornerShape(10.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                Icons.Default.Download,
                                contentDescription = "Export",
                                tint = Color.White
                            )
                            Text(
                                "Export",
                                color = Color.White,
                                fontSize = 15.sp
                            ) // Increased font size
                        }
                    }
                }

                Button(
                    onClick = { onViewFullRecordClick?.invoke() },
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                        .shadow(6.dp, RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Default.Visibility,
                            contentDescription = "View Record",
                            tint = Color.White
                        )
                        Text("View", color = Color.White, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}


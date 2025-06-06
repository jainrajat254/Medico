package com.example.medico.presentation.ui.screens.doctor

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.presentation.ui.navigation.DocBottomNavBar
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.presentation.ui.screens.BackgroundContentHome
import com.example.medico.presentation.ui.screens.CurrentPatientCard
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.viewmodel.AppointmentsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun HomeScreen(
    navController: NavController,
    appointmentsViewModel: AppointmentsViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val id = sharedPreferencesManager.getDocProfile()?.id ?: ""
    var refreshAppointments by remember { mutableStateOf(false) }

    val todayAppointmentsResult by appointmentsViewModel.getAppointmentsForTodayState.collectAsState()
    val absentAppointmentsResult by appointmentsViewModel.absentAppointmentsState.collectAsState()

    LaunchedEffect(id, refreshAppointments) {
        appointmentsViewModel.getAppointmentsForToday(id)
        appointmentsViewModel.getAbsentAppointmentsForToday(id)
        refreshAppointments = false
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedAppointment by remember { mutableStateOf<AppointmentDTO?>(null) }
    var actionType by remember { mutableStateOf("") }

    val sortedAppointments = when (todayAppointmentsResult) {
        is ResultState.Success -> (todayAppointmentsResult as ResultState.Success<List<AppointmentDTO>>).data.sortedBy { it.queueIndex }
        else -> emptyList()
    }

    val absentAppointments = when (absentAppointmentsResult) {
        is ResultState.Success -> (absentAppointmentsResult as ResultState.Success<List<AppointmentDTO>>).data
        else -> emptyList()
    }

    Scaffold(
        bottomBar = {
            DocBottomNavBar(navController = navController, modifier = Modifier)
        }
    ) { paddingValues ->
        BackgroundContentHome(paddingValues = paddingValues, name = "Doctor") {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 4.dp)
            ) {
                if (sortedAppointments.isNotEmpty()) {
                    itemsIndexed(sortedAppointments) { index, appointment ->
                        Spacer(modifier = Modifier.height(if (index == 0) 24.dp else 18.dp))
                        if (index == 0) {
                            CurrentPatientCard(
                                patientName = appointment.patientName,
                                index = appointment.queueIndex,
                                showPersonalInfoOnly = false,
                                onRecordsClick = {
                                    navController.navigate(
                                        Routes.CurrentPatient.createRoutes(
                                            userDetails = appointment,
                                            index = appointment.queueIndex
                                        )
                                    )
                                },
                                onDoneClick = {
                                    selectedAppointment = appointment
                                    actionType = "Done"
                                    showDialog = true
                                },
                                onAbsentClick = {
                                    selectedAppointment = appointment
                                    actionType = "Absent"
                                    showDialog = true
                                }
                            )
                        } else {
                            OtherPatientCard(
                                patientName = appointment.patientName,
                                index = appointment.queueIndex
                            )
                        }
                    }
                } else {
                    item { NotAvailable(label = "No Appointments for Today") }
                }

                if (absentAppointments.isNotEmpty()) {
                    item {
                        Text(
                            text = "Absent Appointments",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    itemsIndexed(absentAppointments) { _, appointment ->
                        CurrentPatientCard(
                            patientName = appointment.patientName,
                            index = appointment.queueIndex,
                            showPersonalInfoOnly = false,
                            onRecordsClick = {
                                navController.navigate(
                                    Routes.CurrentPatient.createRoutes(
                                        userDetails = appointment, index = appointment.queueIndex
                                    )
                                )
                            },
                            onDoneClick = {
                                selectedAppointment = appointment
                                actionType = "Done"
                                showDialog = true
                            },
                            showAbsent = false
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }
            }
        }
    }

    if (showDialog && selectedAppointment != null) {
        ConfirmationDialog(
            actionType = actionType,
            onConfirm = {
                selectedAppointment?.let {
                    if (actionType == "Done") {
                        appointmentsViewModel.markAppointmentAsDone(it.id)
                    } else {
                        appointmentsViewModel.markAppointmentAsAbsent(it.id)
                    }
                    refreshAppointments = true
                }
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}


@Composable
fun ConfirmationDialog(
    actionType: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm $actionType") },
        text = { Text("Are you sure you want to mark this appointment as $actionType?") },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Yes") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}


@Composable
fun OtherPatientCard(patientName: String, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFF4771CC), RoundedCornerShape(16.dp))
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$index. $patientName", fontWeight = FontWeight.Medium, fontSize = 18.sp
            )
        }
    }
}
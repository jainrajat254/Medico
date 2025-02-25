package com.example.medico.doctor.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.medico.common.navigation.DocBottomNavBar
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContentHome
import com.example.medico.common.utils.CurrentPatientCard
import com.example.medico.common.utils.NotAvailable
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.dto.AppointmentDTO
import kotlinx.coroutines.flow.map


@Composable
fun HomeScreen(
    navController: NavController,
    vm: AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val id = sharedPreferencesManager.getDocId() // Keeping only docId from SharedPreferences
    var refreshAppointments by remember { mutableStateOf(false) }

    // Fetch appointments on launch and refresh
    LaunchedEffect(id, refreshAppointments) {
        vm.getTodaysAppointments(id)
        vm.getTodaysAbsentAppointments(id)
        refreshAppointments = false
    }

    val todayAppointments by vm.todaysAppointments.collectAsStateWithLifecycle()
    val absentAppointments by vm.absentAppointments.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var selectedAppointment by remember { mutableStateOf<AppointmentDTO?>(null) }
    var actionType by remember { mutableStateOf("") }

    Scaffold(bottomBar = {
        DocBottomNavBar(navController = navController, modifier = Modifier)
    }) { paddingValues ->
        BackgroundContentHome(paddingValues = paddingValues, name = "Doctor") {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 4.dp)
            ) {
                val sortedAppointments = todayAppointments.sortedBy { it.queueIndex }

                if (sortedAppointments.isNotEmpty()) {
                    itemsIndexed(sortedAppointments) { index, appointment ->
                        Spacer(modifier = Modifier.padding(top = if (index == 0) 24.dp else 18.dp))

                        if (index == 0) {
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
                    item{ Spacer(modifier = Modifier.padding(top = 8.dp)) }
                    itemsIndexed(absentAppointments) { _, appointment ->
                        CurrentPatientCard(
                            patientName = appointment.patientName,
                            index = appointment.queueIndex, // Using queueIndex directly
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
                            Spacer(modifier = Modifier.padding(top = 18.dp))
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
                        vm.markAppointmentAsDone(it)
                    } else {
                        vm.markAppointmentAsAbsent(it)
                    }
                    refreshAppointments = true // Refresh list after action
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
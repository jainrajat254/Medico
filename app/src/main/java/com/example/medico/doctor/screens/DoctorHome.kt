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

@Composable
fun HomeScreen(
    navController: NavController,
    vm: AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val id = sharedPreferencesManager.getDocId()
    var refreshAppointments by remember { mutableStateOf(false) }

    var idx by rememberSaveable { mutableIntStateOf(sharedPreferencesManager.getIndex()) }

    LaunchedEffect(id, refreshAppointments) {
        if (sharedPreferencesManager.isNewDay()) {
            idx = 1  // ✅ Reset index for new day
            sharedPreferencesManager.saveIndex(1)
            sharedPreferencesManager.saveLastOpenedDate()  // ✅ Update stored date
        }
        vm.getTodaysAppointments(id)
        refreshAppointments = false  // Reset after fetching
    }

    val todayAppointments by vm.todaysAppointments.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var selectedAppointment by remember { mutableStateOf<AppointmentDTO?>(null) }

    Scaffold(bottomBar = {
        DocBottomNavBar(
            navController = navController, modifier = Modifier
        )
    }) { paddingValues ->
        BackgroundContentHome(paddingValues = paddingValues, name = "Doctor") {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 4.dp)
            ) {
                if (todayAppointments.isNotEmpty()) {
                    itemsIndexed(todayAppointments) { index, appointment ->
                        Spacer(modifier = Modifier.padding(top = if (index == 0) 24.dp else 18.dp))
                        if (index == 0) {
                            CurrentPatientCard(
                                patientName = appointment.patientName,
                                index = idx,  // ✅ Using idx instead of index
                                showPersonalInfoOnly = false,
                                onRecordsClick = {
                                    navController.navigate(
                                        Routes.CurrentPatient.createRoutes(
                                            userDetails = appointment, index = idx
                                        )
                                    )
                                },
                                onDoneClick = {
                                    selectedAppointment = appointment
                                    showDialog = true
                                },
                                onAbsentClick = { /* Handle Absent */ }
                            )
                        } else {
                            OtherPatientCard(
                                patientName = appointment.patientName,
                                index = idx + index  // ✅ Keep incrementing idx instead of index+1
                            )
                        }
                    }
                } else {
                    item { NotAvailable(label = "No Appointments for Today") }
                }
            }
        }
    }

    if (showDialog && selectedAppointment != null) {
        ConfirmationDialog(
            onConfirm = {
                vm.markAppointmentAsDone(selectedAppointment!!)
                showDialog = false
                idx++  // ✅ Increment idx
                sharedPreferencesManager.saveIndex(idx)  // ✅ Save new index
                refreshAppointments = true  // ✅ Trigger data reload
            },
            onDismiss = { showDialog = false }
        )
    }
}



@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text("Confirm Completion") },
        text = { Text("Are you sure you want to mark this appointment as done?") },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        })
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

package com.example.medico.doctor.screens

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.common.navigation.DocBottomNavBar
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContentHome
import com.example.medico.common.utils.CurrentPatientCard
import com.example.medico.common.utils.NotAvailable
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.dto.AppointmentDTO
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    navController: NavController,
    vm: AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val id = sharedPreferencesManager.getDocId()
    LaunchedEffect(id) {
        vm.getDoctorAppointments(id)
    }
    val appointments by vm.docAppointments.collectAsState()

    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")

    // Filter today's appointments and sort by booking time
    val todayAppointments = appointments
        .mapNotNull { appointment ->
            try {
                val appointmentDate = LocalDate.parse(appointment.date, formatter)
                if (appointmentDate.isEqual(today)) appointment else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        .sortedBy { it.appointmentBookingTime }

    Scaffold(
        bottomBar = {
            DocBottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { paddingValues ->
        BackgroundContentHome(paddingValues = paddingValues, name = "Doctor") {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 4.dp)
            ) {
                if (todayAppointments.isNotEmpty()) {
                    todayAppointments.forEachIndexed { index, appointment ->
                        item {
                            Spacer(modifier = Modifier.padding(top = if (index == 0) 24.dp else 18.dp))
                            if (index == 0) {
                                CurrentPatientCard(
                                    patientName = appointment.patientName,
                                    index = index + 1,
                                    showPersonalInfoOnly = false,
                                    onRecordsClick = { navController.navigate(Routes.CurrentPatient.createRoutes(userDetails = appointment,index = index + 1)) },
                                    onDoneClick = { /* Handle Done */ },
                                    onAbsentClick = { /* Handle Absent */ }
                                )
                            } else {
                                OtherPatientCard(
                                    patientName = appointment.patientName,
                                    index = index + 1
                                )
                            }
                        }
                    }
                } else {
                    item { NotAvailable(label = "No Appointments for Today") }
                }
            }
        }
    }
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
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$index. $patientName",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp

            )
        }
    }
}



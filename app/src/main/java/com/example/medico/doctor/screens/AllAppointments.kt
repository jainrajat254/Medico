package com.example.medico.doctor.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.common.navigation.DocBottomNavBar
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.NotAvailable
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.dto.AppointmentDTO
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllAppointmentsScreen(
    vm: AuthViewModel,
    navController: NavController,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val id = sharedPreferencesManager.getDocId()
    LaunchedEffect(id) {
        vm.getFutureAppointments(id)
    }
    val appointments by vm.futureAppointments.collectAsState()

    val groupedAppointments = appointments.groupBy { it.date }

    Scaffold(
        bottomBar = {
            DocBottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Upcoming Appointments",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 60.dp, top = 20.dp)
                ) {
                    if (groupedAppointments.isNotEmpty()) {
                        groupedAppointments.forEach { (date, appointmentsForDate) ->
                            item {
                                var expanded by remember { mutableStateOf(false) }

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "Date: $date",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )

                                    Button(
                                        onClick = { expanded = !expanded },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(if (expanded) "Hide Appointments" else "Show Appointments")
                                    }

                                    if (expanded) {
                                        appointmentsForDate.forEachIndexed { index, appointment ->
                                            AppointmentCard(appointment, index + 1)
                                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        item { NotAvailable(label = "No Upcoming Appointments") }
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(appointment: AppointmentDTO, queueNumber: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Patient: ${appointment.patientName}", fontWeight = FontWeight.Bold)
            Text(text = "Queue Number: $queueNumber", fontWeight = FontWeight.Bold)
        }
    }
}

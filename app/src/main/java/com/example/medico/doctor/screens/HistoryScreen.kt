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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    vm: AuthViewModel,
    navController: NavController,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val id = sharedPreferencesManager.getDocId()

    LaunchedEffect(id) {
        vm.getPastAppointments(id)
    }

    val pastAppointments by vm.pastAppointments.collectAsState()

    Scaffold(
        bottomBar = { DocBottomNavBar(modifier = Modifier, navController = navController) }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Past Appointments",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                if (pastAppointments.isNotEmpty()) {
                    // ✅ Sorting and grouping BEFORE LazyColumn for efficiency
                    val groupedAppointments = remember(pastAppointments) {
                        pastAppointments
                            .sortedBy { it.appointmentBookingTime }
                            .groupBy { it.date }
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 60.dp, top = 20.dp)
                    ) {
                        groupedAppointments.forEach { (date, appointmentsForDate) ->
                            item {
                                var expanded by remember { mutableStateOf(false) } // ✅ Per date toggle state

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
                    }
                } else {
                    NotAvailable(label = "No Past Appointments")
                }
            }
        }
    }
}


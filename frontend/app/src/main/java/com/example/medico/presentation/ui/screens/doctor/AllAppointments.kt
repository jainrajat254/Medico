package com.example.medico.presentation.ui.screens.doctor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.AppointmentStatus
import com.example.medico.presentation.ui.navigation.DocBottomNavBar
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.AppointmentsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun AllAppointmentsScreen(
    appointmentsViewModel: AppointmentsViewModel,
    navController: NavController,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val id = sharedPreferencesManager.getDocProfile()?.id.orEmpty()

    LaunchedEffect(id) {
        appointmentsViewModel.getFutureAppointments(id)
    }

    val futureAppointmentsResult by appointmentsViewModel.futureAppointmentsState.collectAsState()
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

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
                    when (futureAppointmentsResult) {
                        is ResultState.Loading -> {
                            item {
                                CustomLoader()
                            }
                        }

                        is ResultState.Success -> {
                            val appointments =
                                (futureAppointmentsResult as ResultState.Success).data
                            val groupedAppointments = appointments.groupBy { it.date }

                            if (groupedAppointments.isNotEmpty()) {
                                groupedAppointments.forEach { (date, appointmentsForDate) ->
                                    item {
                                        val expanded = expandedStates[date] ?: false

                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            Text(
                                                text = "Date: $date",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,
                                                modifier = Modifier.padding(vertical = 8.dp)
                                            )

                                            Button(
                                                onClick = {
                                                    expandedStates[date] = !expanded
                                                },
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(if (expanded) "Hide Appointments" else "Show Appointments")
                                            }

                                            if (expanded) {
                                                appointmentsForDate.forEachIndexed { index, appointment ->
                                                    AppointmentCard(
                                                        appointment,
                                                        appointment.queueIndex,
                                                        appointment.status
                                                    )
                                                    HorizontalDivider(
                                                        modifier = Modifier.padding(
                                                            vertical = 4.dp
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                item { NotAvailable(label = "No Upcoming Appointments") }
                            }
                        }

                        is ResultState.Error -> {
                            item {
                                Text(
                                    text = "Something went wrong. Try again.",
                                    color = Color.Red,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        else -> {
                            item { NotAvailable(label = "No Upcoming Appointments") }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AppointmentCard(
    appointment: AppointmentDTO,
    queueNumber: Int,
    status: AppointmentStatus,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp) // Softer rounded corners
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$queueNumber → ${appointment.patientName}", // ✅ Proper arrow usage
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = status.name.replace("_", " ").lowercase()
                    .replaceFirstChar { it.uppercase() }, // ✅ Better status formatting
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = when (status) { // ✅ Status-based color coding
                    AppointmentStatus.BOOKED -> MaterialTheme.colorScheme.primary
                    AppointmentStatus.COMPLETED -> Color(0xFF4CAF50) // Green
                    AppointmentStatus.ABSENT -> Color(0xFFFF9800) // Orange
                }
            )
        }
    }
}



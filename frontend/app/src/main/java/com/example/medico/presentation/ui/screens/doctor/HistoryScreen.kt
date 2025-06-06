package com.example.medico.presentation.ui.screens.doctor

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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.presentation.ui.navigation.DocBottomNavBar
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.AppointmentsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun HistoryScreen(
    appointmentsViewModel: AppointmentsViewModel,
    navController: NavController,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val id = sharedPreferencesManager.getDocProfile()?.id ?: ""
    val pastAppointmentsResult by appointmentsViewModel.pastAppointmentsState.collectAsState()
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    LaunchedEffect(id) {
        appointmentsViewModel.getPastAppointments(id)
    }

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

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 60.dp, top = 20.dp)
                ) {
                    when (pastAppointmentsResult) {
                        is ResultState.Loading -> {
                            item {
                                CustomLoader()
                            }
                        }

                        is ResultState.Success -> {
                            val appointments = (pastAppointmentsResult as ResultState.Success).data
                            val groupedAppointments = appointments
                                .groupBy { it.date }
                                .toSortedMap(compareByDescending { it })

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
                                                onClick = { expandedStates[date] = !expanded },
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(if (expanded) "Hide Appointments" else "Show Appointments")
                                            }

                                            if (expanded) {
                                                appointmentsForDate
                                                    .sortedBy { it.queueIndex }
                                                    .forEach { appointment ->
                                                        AppointmentCard(
                                                            appointment = appointment,
                                                            queueNumber = appointment.queueIndex,
                                                            status = appointment.status
                                                        )
                                                        HorizontalDivider(
                                                            modifier = Modifier.padding(vertical = 4.dp)
                                                        )
                                                    }
                                            }
                                        }
                                    }
                                }
                            } else {
                                item { NotAvailable(label = "No Past Appointments") }
                            }
                        }

                        is ResultState.Error -> {
                            item {
                                Text(
                                    text = "Something went wrong. Please try again.",
                                    color = Color.Red,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        else -> {
                            item { NotAvailable(label = "No Past Appointments") }
                        }
                    }
                }
            }
        }
    }
}


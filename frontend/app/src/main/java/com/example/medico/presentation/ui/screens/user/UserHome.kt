package com.example.medico.presentation.ui.screens.user

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.R
import com.example.medico.domain.model.AppointmentsResponse
import com.example.medico.domain.model.MedicationResponse
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.presentation.ui.navigation.UserBottomNavBar
import com.example.medico.presentation.ui.screens.BackgroundContentHome
import com.example.medico.presentation.ui.screens.NotAvailable
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.AppointmentsViewModel
import com.example.medico.presentation.viewmodel.MedicationsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun UserHomePage(
    navController: NavController,
    sharedPreferencesManager: SharedPreferencesManager,
    medicationsViewModel: MedicationsViewModel,
    appointmentsViewModel: AppointmentsViewModel,
) {
    val user = sharedPreferencesManager.getUserProfile()
    Log.d("User Details", "$user")
    Scaffold(
        bottomBar = {
            UserBottomNavBar(modifier = Modifier, navController = navController)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Routes.UserAppointments.routes) },
                containerColor = Color(0xFF4771CC),
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Image(
                    painter = painterResource(id = R.drawable.appointment),
                    contentDescription = "appointments",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Appointments")
            }
        }
    ) { paddingValues ->
        BackgroundContentHome(
            paddingValues = paddingValues,
            name = user?.firstName ?: ""
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {

                item {
                    CurrentMedicationList(
                        sharedPreferencesManager = sharedPreferencesManager,
                        medicationsViewModel = medicationsViewModel,
                        navController
                    )
                }

                item {
                    HorizontalDivider(
                        color = Color.Black,
                        thickness = 2.dp,
                        modifier = Modifier.padding(top = 30.dp)
                    )
                }

                item {
                    AppointmentsList(sharedPreferencesManager, appointmentsViewModel)
                }

                item {
                    HealthCardsRow()
                }
            }
        }
    }
}

@Composable
fun CurrentMedicationList(
    sharedPreferencesManager: SharedPreferencesManager,
    medicationsViewModel: MedicationsViewModel,
    navController: NavController,
) {
    val id = sharedPreferencesManager.getUserProfile()?.id ?: ""
    val medicationsState by medicationsViewModel.getMedicationsState.collectAsState()

    LaunchedEffect(id) {
        medicationsViewModel.getMedications(id)
    }

    when (medicationsState) {
        is ResultState.Loading -> {
            CustomLoader()
        }

        is ResultState.Error -> {
            Text(
                text = (medicationsState as ResultState.Error).error.message.toString(),
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        is ResultState.Success -> {
            val medications = (medicationsState as ResultState.Success).data

            if (medications.isEmpty()) {
                NotAvailable(label = "No medication to show")
            } else {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(medications) { medication ->
                        MedicationItem(medication, navController)
                    }
                }
            }
        }

        else -> {
            // Handle Idle or other states if needed
        }
    }
}

@Composable
fun MedicationItem(medication: MedicationResponse, navController: NavController) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .wrapContentHeight()
            .border(
                width = 2.dp, color = Color(0xFF4771CC), shape = RoundedCornerShape(12.dp)
            )
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Title
            Text(
                text = "Current Medication",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333)
            )

            // Divider
            HorizontalDivider(
                color = Color(0xFFCCCCCC),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            // Medication Name (Clickable)
            Text(
                text = medication.medicationName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4771CC),
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .clickable {
                        navController.navigate(Routes.CurrentMed.routes)
                    }
            )

            // Prescribed By
            Text(
                text = "Prescribed by: ${medication.doctorName}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun AppointmentsList(
    sharedPreferencesManager: SharedPreferencesManager,
    appointmentsViewModel: AppointmentsViewModel,
) {
    val id = sharedPreferencesManager.getUserProfile()?.id ?: ""
    val appointmentState by appointmentsViewModel.getAppointmentState.collectAsState()

    LaunchedEffect(id) {
        appointmentsViewModel.getAppointments(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        when (appointmentState) {
            is ResultState.Loading -> {
                CustomLoader()
            }

            is ResultState.Error -> {
                Text(
                    text = (appointmentState as ResultState.Error).error.message.toString(),
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            is ResultState.Success -> {
                val appointments = (appointmentState as ResultState.Success).data

                if (appointments.isEmpty()) {
                    NotAvailable(label = "No Appointments to show")
                } else {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(appointments) { appointment ->
                            AppointmentCard(appointment)
                        }
                    }
                }
            }

            else -> {
                // Handle Idle or custom state if needed
            }
        }
    }
}


@Composable
fun AppointmentCard(appointment: AppointmentsResponse) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
            .padding(top = 20.dp)
            .border(
                width = 2.dp, color = Color(0xFF4771CC), shape = RoundedCornerShape(12.dp)
            )
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Title
            Text(
                text = "Appointment",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            HorizontalDivider(
                color = Color(0xFFCCCCCC),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            // Doctor Name and Queue Index
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dr. ${appointment.doctorName}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4771CC)
                )

                Text(
                    text = "#${appointment.queueIndex}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4771CC)
                )
            }

            // Workspace Name
            Text(
                text = appointment.workspaceName,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
            )

            // Specialization
            Text(
                text = appointment.specialization,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Date and Time Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = appointment.date,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666)
                )

                Text(
                    text = appointment.time, // Added Time Display
                    fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF666666)
                )
            }
        }
    }
}


@Composable
fun HealthCardsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // First Health Card
        HealthCard(
            title = "Heart Rate",
            value = "72",
            unit = "bpm",
            color = Color(0xFF50CC88),
            painter = painterResource(id = R.drawable.heart)
        )

        // Second Health Card
        HealthCard(
            title = "Blood Pressure",
            value = "120/80",
            unit = "",
            color = Color(0xFF50CC88),
            painter = painterResource(id = R.drawable.bp)
        )
    }
}

@Composable
fun HealthCard(
    title: String,
    value: String,
    unit: String,
    color: Color,
    painter: Painter,
) {
    Card(
        modifier = Modifier
            .size(167.dp, 150.dp)
            .border(
                width = 2.dp, color = Color(0xFF4771CC), shape = RoundedCornerShape(16.dp)
            )
            .shadow(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
            Text(
                text = "$value $unit",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(top = 12.dp)
            )
        }
    }
}

package com.example.medico.user.screens

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
import androidx.compose.foundation.layout.height
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
import com.example.medico.common.navigation.Routes
import com.example.medico.common.navigation.UserBottomNavBar
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContentHome
import com.example.medico.common.utils.NotAvailable
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.responses.AppointmentsResponse
import com.example.medico.user.responses.MedicationResponse

@Composable
fun UserHomePage(
    navController: NavController,
    sharedPreferencesManager: SharedPreferencesManager,
    vm: AuthViewModel,
) {
    val user = sharedPreferencesManager.getUserFromSharedPreferences()
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
                        vm = vm,
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
                    AppointmentsList(sharedPreferencesManager, vm)
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
    vm: AuthViewModel,
    navController: NavController,
) {
    val id = sharedPreferencesManager.getUserId()
    val medications by vm.medications.collectAsState()

    LaunchedEffect(id) {
        id.let { vm.getMedication(it) }
    }

    if (medications.isEmpty()) {
        NotAvailable(label = "No medication to show")
    } else {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(medications) { medication ->
                MedicationItem(medication, navController)
                Spacer(modifier = Modifier.height(16.dp)) // 🚨 `Spacer` inside `items` has no effect
            }
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
fun AppointmentsList(sharedPreferencesManager: SharedPreferencesManager, vm: AuthViewModel) {
    val id = sharedPreferencesManager.getUserId()
    val appointments by vm.appointments.collectAsState()

    LaunchedEffect(id) {
        vm.getAppointments(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
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

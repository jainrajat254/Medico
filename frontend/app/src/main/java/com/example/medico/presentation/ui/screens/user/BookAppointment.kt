package com.example.medico.presentation.ui.screens.user

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medico.domain.model.Appointments
import com.example.medico.domain.model.DoctorDTO
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.CustomTextField
import com.example.medico.presentation.ui.screens.SlotDropdown
import com.example.medico.presentation.ui.screens.showDatePicker
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun BookAppointment(
    navController: NavController,
    doctorDetails: DoctorDTO,
    appointmentsViewModel: com.example.medico.presentation.viewmodel.AppointmentsViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {

    val userId = sharedPreferencesManager.getUserProfile()?.id ?: ""
    val context = LocalContext.current
    var date by remember { mutableStateOf("") }
    var slot by remember { mutableStateOf("") }
    var isDialog by remember {
        mutableStateOf(false)
    }

    val addAppointmentState by appointmentsViewModel.addAppointmentState.collectAsState()

    LaunchedEffect(addAppointmentState) {
        when (addAppointmentState) {
            is ResultState.Success -> {
                isDialog = true
                Toast.makeText(context, "Appointment booked!", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.UserHome.routes) {
                    popUpTo(Routes.UserHome.routes) { inclusive = true }
                }
            }

            is ResultState.Error -> {
                isDialog = false
                Log.d(
                    "BOOK_APPOINTMENT_ERROR",
                    (addAppointmentState as ResultState.Error).error.toString()
                )
                Toast.makeText(context, "Failed to book appointment", Toast.LENGTH_SHORT).show()
            }

            is ResultState.Idle -> isDialog = false
            is ResultState.Loading -> isDialog = true
        }
    }


    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    CustomTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = "Date",
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePicker(context) { selectedDate ->
                                    date = selectedDate
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Pick Date"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )
                }
                item {
                    SlotDropdown(
                        selectedSlot = slot,
                        availableSlots = doctorDetails.workingTime,
                        onSlotSelected = { slot = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )
                }
                item {
                    Button(
                        onClick = {
                            if (date.isBlank() || slot.isBlank()) {
                                Toast.makeText(context, "Please select both date and slot", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            Log.d("USERID :", userId)
                            if (userId.isNotEmpty()) {
                                val data = Appointments(
                                    doctorName = "${doctorDetails.firstName} ${doctorDetails.lastName}",
                                    patientName = sharedPreferencesManager.getUserName(),
                                    specialization = doctorDetails.specialization,
                                    workspaceName = doctorDetails.workspaceName,
                                    doctorId = doctorDetails.id,
                                    date = date,
                                    time = slot,
                                )
                                Log.d("DOC ID", doctorDetails.id)
                                appointmentsViewModel.addAppointments(request = data, id = userId)
                            } else {
                                Toast.makeText(context, "User ID not found!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Text(text = "Book Appointment")
                    }

                }
            }
        }
        if (isDialog) {
            CustomLoader()
        }
    }
}



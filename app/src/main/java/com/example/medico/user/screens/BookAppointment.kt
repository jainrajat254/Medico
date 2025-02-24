package com.example.medico.user.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.CustomTextField
import com.example.medico.common.utils.SlotDropdown
import com.example.medico.common.utils.showDatePicker
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.doctor.dto.DoctorDTO
import com.example.medico.user.model.Appointments

@Composable
fun BookAppointment(
    navController: NavController,
    doctorDetails: DoctorDTO,
    vm: AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {

    val context = LocalContext.current
    var date by remember { mutableStateOf("") }
    var slot by remember { mutableStateOf("") }

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
                            val userId = sharedPreferencesManager.getUserId()
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
                                Log.d("DOC ID",doctorDetails.id)
                                vm.addAppointments(data, id = userId) { isSuccess, message ->
                                    if (isSuccess) {
                                        Toast.makeText(context, "Appointment booked successfully!", Toast.LENGTH_SHORT).show()
                                        navController.navigate(Routes.UserHome.routes) {
                                            popUpTo(Routes.UserHome.routes) { inclusive = true }
                                        }
                                    } else {
                                        Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(context, "User ID not found!", Toast.LENGTH_SHORT).show()
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
    }
}


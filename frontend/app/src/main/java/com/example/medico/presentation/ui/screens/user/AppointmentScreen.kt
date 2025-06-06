package com.example.medico.presentation.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.domain.model.Districts
import com.example.medico.domain.model.DoctorDTO
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.CommonDropDownMenu
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.ResultState

@Composable
fun DoctorAppointmentScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel,
) {
    var showFilterSheet by remember { mutableStateOf(false) }
    val getDoctorsState by settingsViewModel.getDoctorsState.collectAsState()
    val searchQuery by settingsViewModel.searchQuery.collectAsState()
    val filteredDoctors by settingsViewModel.filteredDoctors.collectAsState()
    val context = LocalContext.current

    // Trigger fetch once
    LaunchedEffect(Unit) {
        settingsViewModel.getDoctors()
    }

    // React to changes in fetch state
    LaunchedEffect(getDoctorsState) {
        when (val result = getDoctorsState) {
            is ResultState.Success -> {
                settingsViewModel.setAllDoctors(result.data) // original full list
                settingsViewModel.applyFilters() // apply filters based on query/state
            }

            is ResultState.Error -> {
                Toast.makeText(
                    context,
                    "Error fetching doctors: ${result.error.message ?: "Unknown error"}",
                    Toast.LENGTH_LONG
                ).show()
            }

            ResultState.Loading -> {
                // Optional: show loading spinner
            }

            ResultState.Idle -> Unit
        }
    }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues, showTagline = false) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                // Search and Filter Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = searchQuery.text,
                        onValueChange = {
                            settingsViewModel.updateSearchQuery(TextFieldValue(it))
                            settingsViewModel.applyFilters()
                        },
                        label = { Text("Search Doctor, Specialization, or Address") },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.FilterAlt,
                                contentDescription = null,
                                modifier = Modifier.clickable { showFilterSheet = true }
                            )
                        },
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF5892CA),
                            unfocusedContainerColor = Color(0xFF5892CA),
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = Color.Black
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Doctor List
                when (getDoctorsState) {
                    is ResultState.Loading -> {
                        CustomLoader()
                    }

                    is ResultState.Error,
                    is ResultState.Idle,
                    is ResultState.Success,
                    -> {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            if (filteredDoctors.isEmpty()) {
                                item {
                                    Text(
                                        text = "No doctors found",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            } else {
                                items(filteredDoctors) { doctor ->
                                    DoctorCard(doctor, navController)
                                }
                            }
                        }
                    }
                }

                // Filter Bottom Sheet
                if (showFilterSheet) {
                    FilterBottomSheet(
                        selectedState = settingsViewModel.selectedState,
                        selectedDistrict = settingsViewModel.selectedDistrict,
                        onStateChange = {
                            settingsViewModel.updateState(it)
                            settingsViewModel.applyFilters()
                        },
                        onDistrictChange = {
                            settingsViewModel.updateDistrict(it)
                            settingsViewModel.applyFilters()
                        },
                        onDismiss = { showFilterSheet = false }
                    )
                }
            }
        }
    }
}


@Composable
fun DoctorCard(doctor: DoctorDTO, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color(0xFF4771CC),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${doctor.firstName} ${doctor.lastName}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${doctor.specialization}, ${doctor.qualification}",
                fontSize = 14.sp
            )
            Text(
                text = "${doctor.district}, ${doctor.state} -${doctor.zipCode}",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        navController.navigate(Routes.BookAppointment.createRoutes(doctor)) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Book Appointment", color = Color.White, fontSize = 12.sp)
                }

                Button(
                    onClick = {
                        navController.navigate(Routes.DoctorOverview.createRoutes(doctor)) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "About",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    selectedState: String,
    selectedDistrict: String,
    onStateChange: (String) -> Unit,
    onDistrictChange: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Filter Options", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            // State Dropdown
            val states = Districts.districts.keys.toList()
            CommonDropDownMenu(
                label = "Select State",
                selectedItem = selectedState,
                items = states,
                onItemSelected = {
                    onStateChange(it)
                    onDistrictChange("")
                }
            )

            // District Dropdown
            val districts = Districts.districts[selectedState] ?: emptyList()
            CommonDropDownMenu(
                label = "Select District",
                selectedItem = selectedDistrict,
                items = districts,
                onItemSelected = onDistrictChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Clear Filters Button
                TextButton(onClick = {
                    onStateChange("")
                    onDistrictChange("")
                }) {
                    Text("Clear Filters", color = Color.Red)
                }

                // Apply Filters Button
                Button(onClick = onDismiss) {
                    Text("Apply Filters")
                }
            }
        }
    }
}



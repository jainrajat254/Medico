package com.example.medico.user.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.common.model.Districts
import com.example.medico.common.navigation.Routes
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.CommonDropDownMenu
import com.example.medico.doctor.dto.DoctorDTO
import com.example.medico.user.viewModel.AppointmentsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun DoctorAppointmentScreen(navController: NavController) {
    val viewModel: AppointmentsViewModel = getViewModel()
    val doctors by viewModel.filteredDoctors.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues, showTagline = false) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val searchQuery by viewModel.searchQuery.collectAsState()

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.updateSearchQuery(it) },
                        label = { Text(text = "Search Doctor, Specialization, or Address") },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.FilterAlt,
                                contentDescription = null,
                                modifier = Modifier.clickable { showFilterSheet = true }
                            )
                        },
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier.weight(1f),
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

                LazyColumn(modifier = Modifier.weight(1f)) {
                    if (doctors.isEmpty()) {
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
                        items(doctors) { doctor ->
                            DoctorCard(doctor, navController)
                        }
                    }
                }
            }

            // Filter Bottom Sheet
            if (showFilterSheet) {
                FilterBottomSheet(
                    selectedState = viewModel.selectedState,
                    selectedDistrict = viewModel.selectedDistrict,
                    onStateChange = viewModel::updateState,
                    onDistrictChange = viewModel::updateDistrict,
                    onDismiss = { showFilterSheet = false }
                )
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
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Book Appointment", color = Color.White, fontSize = 12.sp)
                }

                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "About",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.clickable {
                            navController.navigate(Routes.DoctorOverview.createRoutes(doctor)) {
                                launchSingleTop = true
                            }
                        }
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



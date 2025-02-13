package com.example.medico.doctor.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.common.navigation.DocBottomNavBar
import com.example.medico.common.utils.BackgroundContentHome
import com.example.medico.common.utils.TaglineAndProfilePicture

@Composable
fun HomeScreen(
    navController: NavController
) {
    val patients = listOf(
        "Harivansh Rai Bachchan",
        "Amitabh Bachchan",
        "Dr. A.P.J. Abdul Kalam",
        "Sardar Vallabhbhai Patel",
        "Rani Lakshmi Bai",
        "Subhash Chandra Bose"
    )

    Scaffold(
        bottomBar = {
            DocBottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { paddingValues ->
        BackgroundContentHome(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Text(
                        text = "Welcome, Doctor",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 64.dp)
                    )
                }
                item { TaglineAndProfilePicture() }

                // Current Patient (First in List)
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    CurrentPatientCard(
                        patientName = patients.first(),
                        index = 1, // Always first patient
                        appointmentTime = "10:15 AM"
                    )
                }

                // Other Patients (From second onwards)
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    OtherPatientsList(patients.drop(1)) // Exclude first patient
                }
            }
        }
    }
}

@Composable
fun CurrentPatientCard(patientName: String, index: Int, appointmentTime: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFF4771CC), RoundedCornerShape(16.dp))
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Current Patient",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = appointmentTime,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = patientName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "#$index", // Display index instead of age
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between buttons
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = { /* Handle Export Action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp) // Rounded corners for buttons
                ) {
                    Text("Records", color = Color.White, fontSize = 12.sp)
                }
                // Button for exporting report
                Button(
                    onClick = { /* Handle Export Action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp) // Rounded corners for buttons
                ) {
                    Text("Done", color = Color.White, fontSize = 12.sp)
                }

                // Button for viewing full report
                Button(
                    onClick = { /* Handle View Full Report Action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp) // Rounded corners for buttons
                ) {
                    Text("Absent", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun OtherPatientsList(patientNames: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Next Patients",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp, start = 8.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(patientNames) { index, patient ->
                OtherPatientCard(patientName = patient, index = index + 2) // Start index from 2
            }
        }
    }
}

@Composable
fun OtherPatientCard(patientName: String, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFF4771CC), RoundedCornerShape(16.dp))
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$index. $patientName",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp

            )
        }
    }
}


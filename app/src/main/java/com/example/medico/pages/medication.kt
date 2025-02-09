package com.example.medico.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.medico.R
import com.example.medico.navigation.BottomNavBar
import com.example.medico.navigation.Routes
import com.example.medico.data.Medication

@Composable
fun MedicationPage(navController: NavHostController) {

    val medications = listOf(
        Medication("Amoxicillin", "500mg", "Take 1 capsule every 8 hours"),
        Medication("Loratadine", "10mg", "Take 1 tablet daily"),
        Medication("Metformin", "1000mg", "Take 1 tablet after meals"),
        Medication("Omeprazole", "20mg", "Take 1 tablet before meals"),
        Medication("Ciprofloxacin", "500mg", "Take 1 tablet every 12 hours"),
        Medication("Gabapentin", "300mg", "Take 1 capsule 3 times daily for nerve pain"),
        Medication("Warfarin", "5mg", "Take as directed for blood thinning"),
        Medication("Vitamin D3", "2000IU", "Take 1 capsule daily"),
        Medication("Propranolol", "40mg", "Take 1 tablet twice daily"),
        Medication("Ranitidine", "150mg", "Take 1 tablet before meals for heartburn")
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.background_app),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                HeaderSection()
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Tagline()

                    // Medical Reports Title
                    Text(
                        text = "Medications",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp) // Space between title and list
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(medications) { medication ->
                            MedicationCard(medication = medication)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }

                // Add and Update Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.navigate(Routes.MedAdd.routes) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

@Composable
fun Tagline() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Health, Your History",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Secure, Simple, Smart!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MedicationCard(medication: Medication) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Placeholder for medication image
            Image(
                painter = painterResource(id = R.drawable.capsule), // Replace with your drawable
                contentDescription = "Medication Icon",
                modifier = Modifier.size(48.dp)
            )

            // Medication Info
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = medication.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4771CC)
                )
                Text(text = medication.dosage, fontSize = 16.sp, color = Color.Gray)
                Text(text = medication.instructions, fontSize = 14.sp, color = Color.DarkGray)
            }
        }
    }
}


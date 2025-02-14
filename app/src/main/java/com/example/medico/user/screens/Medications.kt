package com.example.medico.user.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.medico.common.model.Medication
import com.example.medico.common.navigation.UserBottomNavBar
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.MedicationCard

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

@Composable
fun MedicationPage(navController: NavHostController) {

    Scaffold(
        bottomBar = {
            UserBottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
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
    }
}

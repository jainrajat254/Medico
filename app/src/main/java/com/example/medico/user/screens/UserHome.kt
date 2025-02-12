package com.example.medico.user.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.R
import com.example.medico.common.navigation.BottomNavBar
import com.example.medico.common.model.Medicines
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.HeaderSection
import com.example.medico.common.utils.TaglineAndProfilePicture


val medicines = listOf(
    Medicines("Zyocid 500", "At 6:00 AM"),
    Medicines("Ibuprofen 400", "At 12:00 PM"),
    Medicines("Amoxicillin 500", "At 3:00 PM"),
    Medicines("Aspirin 75", "At 6:00 PM"),
    Medicines("Cetirizine 10", "At 11:00 PM")
)

@Composable
fun HomePage(navController: NavController, sharedPreferencesManager: SharedPreferencesManager) {
    val user = sharedPreferencesManager.getUserFromSharedPreferences()
    Log.d("User Details", "$user")
    Scaffold(
        bottomBar = {
            BottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.background_app),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Header and Profile Picture Section
            HeaderSection()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .padding(start = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Profile Content and LazyColumn for scrollable content
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Text(
                            text = "Welcome, ${user?.firstName}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 64.dp)
                        )
                    }

                    item {
                        TaglineAndProfilePicture()
                    }

                    item {
                        MedicationCard()
                    }

                    item {
                        HorizontalDivider(
                            color = Color.Black,
                            thickness = 2.dp,
                            modifier = Modifier.padding(top = 30.dp)
                        )
                    }

                    item {
                        AppointmentsCard()
                    }

                    item {
                        HealthCardsRow()
                    }
                }
            }
        }
    }
}

@Composable
fun MedicationCard() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(medicines) { medicine ->
            MedicationItem(medicine)
        }
    }
}

@Composable
fun MedicationItem(medication: Medicines) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(130.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF4771CC),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "Next Medication",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                color = Color(0xFF333333)
            )

            // Divider
            HorizontalDivider(
                color = Color(0xFFCCCCCC),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Medication Name
            Text(
                text = medication.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4771CC),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Time
            Text(
                text = medication.time,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun AppointmentsCard() {
    Card(
        modifier = Modifier
            .size(350.dp, 230.dp)
            .padding(top = 30.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF4771CC),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Internal padding for proper spacing
        ) {
            // Title
            Text(
                text = "Appointments",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Divider
            HorizontalDivider(
                color = Color(0xFFCCCCCC),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Doctor Name
            Text(
                text = "Dr. N.K. Garg",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4771CC),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Location
            Text(
                text = "Hospital / Clinic",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Specialization
            Text(
                text = "Cardiology",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Time
            Text(
                text = "At 11:00 A.M.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666666),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
        }
    }
}


// Health Cards Row
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
                width = 2.dp,
                color = Color(0xFF4771CC),
                shape = RoundedCornerShape(16.dp)
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
                contentDescription = null, // Provide a description if needed
                modifier = Modifier
                    .size(50.dp) // Adjust the size as needed
                    .padding(top = 12.dp)
            )
        }
    }
}

package com.example.medico.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.medico.R
import com.example.medico.controllers.MyBottomBar
import com.example.medico.data.HealthRecord

val records = listOf(
    HealthRecord("Cardiology Consultation", "Dr. Santosh Shukla","Stable Cardiovascular health......." ),
    HealthRecord("Laboratory Results", "Dr. B.N Ghosh","Vitamin D deficiency......"),
    HealthRecord("Annual Physical", "Dr. Raghav Sharma","Generally Healthy, Mild Hypertension.......") ,
    HealthRecord("Viral Fever", "Dr. Raghav Sharma","Little Warm......")
)

@Composable
fun HealthRecords(navController: NavHostController) {

    Scaffold(
        bottomBar = {
            MyBottomBar(navController)
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

                    // Medical Records Title
                    Text(
                        text = "Medical Records",
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
                        items(records) { record ->
                            RecordCard(record = record)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecordCard(record: HealthRecord) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)  // Adjusted height to fit content and buttons
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
                .padding(16.dp)
        ) {
            Text(
                text = record.recordName,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D4159)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Reviewed by: ${record.doctorName}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = record.diagnosis,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF4A4A4A)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Handle View Full Record Action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "View Full Record",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

package com.example.medico.user.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.medico.common.model.HealthRecord
import com.example.medico.common.navigation.UserBottomNavBar
import com.example.medico.common.utils.BackgroundContent

val records = listOf(
    HealthRecord(
        "Cardiology Consultation",
        "Dr. Santosh Shukla",
        "Stable Cardiovascular health......."
    ),
    HealthRecord("Laboratory Results", "Dr. B.N Ghosh", "Vitamin D deficiency......"),
    HealthRecord(
        "Annual Physical",
        "Dr. Raghav Sharma",
        "Generally Healthy, Mild Hypertension......."
    ),
    HealthRecord("Viral Fever", "Dr. Raghav Sharma", "Little Warm......")
)

@Composable
fun HealthRecords(navController: NavHostController) {

    Scaffold(
        bottomBar = {
            UserBottomNavBar(modifier = Modifier, navController = navController)
        }
    ) { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
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
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(records) { record ->
                    RecordCard(record = record)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun RecordCard(record: HealthRecord) {
    Card(
        modifier = Modifier
            .width(300.dp) // ✅ Matches AppointmentCard width
            .wrapContentHeight()
            .padding(top = 20.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF4771CC),
                shape = RoundedCornerShape(12.dp)
            )
            .shadow(6.dp, RoundedCornerShape(12.dp)), // ✅ Soft shadow for depth
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
            Text(
                text = record.recordName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4771CC)
            )

            // 🔸 Divider for cleaner separation
            HorizontalDivider(
                color = Color(0xFFCCCCCC),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            Text(
                text = "Reviewed by: Dr. ${record.doctorName}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
            )

            // 🔹 Diagnosis
            Text(
                text = record.diagnosis,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "25/11/2024", // Assuming a date exists
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666)
                )

                Text(
                    text = "9:11 A.M", // Assuming a time exists
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666)
                )
            }
        }
    }
}



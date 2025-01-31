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
import com.example.medico.R
import com.example.medico.controllers.BottomNavBar
import com.example.medico.data.HealthReport

val reports = listOf(
    HealthReport("Blood Sugar(HbA1c", "Dr. Santosh Shukla","Needs Attention !" ),
    HealthReport("Thyroid Profile", "Dr. B.N Ghosh","Normal"),
    HealthReport("Lipid Profile", "Dr. Raghav Sharma","Needs Attention !") ,
    HealthReport("Platelets", "Dr. Raghav Sharma","Needs Attention !")
)

@Composable
fun HealthReports(navController: NavHostController) {
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
                        text = "Medical Reports",
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
                        items(reports) { report ->
                            ReportCard(report = report)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReportCard(report: HealthReport) {
    // Card with increased size
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
                .padding(16.dp) // Padding inside the card
        ) {
            // Report Name
            Text(
                text = report.reportName,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D4159)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Reviewed by: ${report.doctorName}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Attention Text with conditional color
            Text(
                text = report.attention,
                style = TextStyle(
                    color = if (report.attention.contains("Attention", ignoreCase = true)) Color.Red else Color.Green,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp)) // Add space before buttons

            // Row for buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between buttons
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Button for exporting report
                Button(
                    onClick = { /* Handle Export Action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp) // Rounded corners for buttons
                ) {
                    Text("Export", color = Color.White, fontSize = 14.sp)
                }

                // Button for viewing full report
                Button(
                    onClick = { /* Handle View Full Report Action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp) // Rounded corners for buttons
                ) {
                    Text("View Full Report", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}
package com.example.medico.presentation.ui.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.medico.R
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.DetailRow
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.ResultState

@Composable
fun UserOverview(id: String, settingsViewModel: SettingsViewModel) {

    val userDetails by settingsViewModel.getDetailsState.collectAsState()

    LaunchedEffect(id) {
        settingsViewModel.getDetails(id)
    }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 24.dp,
                        top = 0.dp,
                        end = 24.dp,
                        bottom = 8.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    when (userDetails) {
                        is ResultState.Loading ->
                            CustomLoader(text = "Loading user details...")

                        is ResultState.Error -> Text(
                            "Error: ${(userDetails as ResultState.Error).error.message.toString()}",
                            fontSize = 18.sp,
                            color = Color.Red
                        )

                        is ResultState.Success -> {
                            val user = (userDetails as ResultState.Success).data

                            AsyncImage(
                                model = R.drawable.home,
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "${user.firstName} ${user.lastName}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2D4159)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(2.dp, Color(0xFF4771CC), RoundedCornerShape(16.dp))
                                    .shadow(8.dp, RoundedCornerShape(16.dp))
                                    .wrapContentHeight(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    DetailRow(label = "Gender", value = user.gender)
                                    DetailRow(label = "Age", value = user.age)
                                    DetailRow(label = "Blood Group", value = user.bloodGroup)
                                    DetailRow(label = "Height", value = user.height)
                                    DetailRow(label = "Weight", value = user.weight)
                                }
                            }
                        }

                        else -> {
                            Text("No data available.", fontSize = 18.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}


package com.example.medico.doctor.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.medico.R
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.DetailRow
import com.example.medico.user.viewModel.UserOverviewViewModel
import com.example.medico.user.viewModel.UserState

@Composable
fun UserOverview(id: String, vm: UserOverviewViewModel = viewModel()) {
    val userState by vm.userDetails.collectAsState()

    LaunchedEffect(id) {
        vm.fetchUserDetails(id)
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
                    when (userState) {
                        is UserState.Loading -> Text("Loading user details...", fontSize = 18.sp, color = Color.Gray)
                        is UserState.Error -> Text("Error: ${(userState as UserState.Error).message}", fontSize = 18.sp, color = Color.Red)
                        is UserState.Success -> {
                            val user = (userState as UserState.Success).user

                            AsyncImage(
                                model = R.drawable.home,
                                contentDescription = "Profile Image",
                                modifier = Modifier.size(150.dp).clip(CircleShape).background(Color.LightGray),
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
                                modifier = Modifier.fillMaxWidth()
                                    .border(2.dp, Color(0xFF4771CC), RoundedCornerShape(16.dp))
                                    .shadow(8.dp, RoundedCornerShape(16.dp))
                                    .wrapContentHeight(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
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
                    }
                }
            }
        }
    }
}

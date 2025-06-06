package com.example.medico.presentation.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.medico.R

@Composable
fun UserBottomNavBar(
    modifier: Modifier,
    navController: NavController,
) {
    val userItems = listOf(
        BottomNavItem(Routes.UserHome.routes, R.drawable.baseline_home_24, "Home"),
        BottomNavItem(Routes.Medications.routes, R.drawable.capsule, "Medications"),
        BottomNavItem(Routes.Records.routes, R.drawable.records, "Records"),
        BottomNavItem(Routes.Reports.routes, R.drawable.reports, "Reports"),
        BottomNavItem(Routes.UserSettings.routes, R.drawable.settings, "Settings")
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(color = 0XFF3872D3))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter), // Align the Row to the bottom of the Box
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            userItems.forEach { item ->
                BottomNavBarItem(
                    item = item,
                    isSelected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(Routes.UserHome.routes) {
                                inclusive = false
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DocBottomNavBar(
    modifier: Modifier,
    navController: NavController,
) {
    val doctorItems = listOf(
        BottomNavItem(Routes.DoctorHome.routes, R.drawable.baseline_home_24, "Home"),
        BottomNavItem(Routes.History.routes, R.drawable.baseline_history_24, "History"),
        BottomNavItem(Routes.AllAppointmentsScreen.routes, R.drawable.appointment, "Appointments"),
        BottomNavItem(Routes.DocSettings.routes, R.drawable.settings, "Settings")
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color(color = 0XFF3872D3))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter), // Align the Row to the bottom of the Box
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            doctorItems.forEach { item ->
                BottomNavBarItem(
                    item = item,
                    isSelected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(Routes.UserHome.routes) {
                                inclusive = false
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.label,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(if (isSelected) Color.LightGray else Color.Transparent)
        )
        Text(
            text = item.label,
            color = if (isSelected) Color.Black else Color.White,
            fontSize = 12.sp
        )
    }
}

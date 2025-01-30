package com.example.medico.controllers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.medico.R
import com.example.medico.pages.HealthRecords
import com.example.medico.pages.HealthReports
import com.example.medico.pages.HomePage
import com.example.medico.pages.MedicationPage
import com.example.medico.pages.SettingsPage

data class BottomNavItem(
    val title: String,
    val route: String,
    val iconResId: Int // Resource ID of the image
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNav(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute in listOf(
                    Routes.Home.routes,
                    Routes.Medications.routes,
                    Routes.Records.routes,
                    Routes.Reports.routes,
                    Routes.Settings.routes
                )
            ) {
                MyBottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.routes, // Use Home as the start destination instead of Splash for testing
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Home.routes) {
                HomePage(navController)
            }
            composable(Routes.Medications.routes) {
                MedicationPage(navController)
            }
            composable(Routes.Records.routes) {
                HealthRecords(navController = navController)
            }
            composable(Routes.Reports.routes) {
                HealthReports(navController)
            }
            composable(Routes.Reports.routes) {
                SettingsPage(navController)
            }
        }
    }
}


@Composable
fun MyBottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem("Home", Routes.Home.routes, R.drawable.home), // Replace with your actual drawable resources
        BottomNavItem("Medications", Routes.Medications.routes, R.drawable.capsule),
        BottomNavItem("Records", Routes.Records.routes, R.drawable.records),
        BottomNavItem("Reports", Routes.Reports.routes, R.drawable.reports),
        BottomNavItem("Settings", Routes.Settings.routes, R.drawable.settings)
    )

    BottomAppBar(modifier = Modifier.height(64.dp),
        containerColor = Color(color = 0XFF3872D3) ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = item.title,
                        modifier = Modifier.size(32.dp )
                    )
                }
            )
        }
    }
}

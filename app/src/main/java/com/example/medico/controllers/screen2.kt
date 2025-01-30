package com.example.medico.controllers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medico.R

@Preview(showBackground = true)
@Composable
fun SideDrawer() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(Color.White)
            .padding(8.dp)
    ) {
        DrawerHeader()

        Spacer(modifier = Modifier.height(16.dp))

        DrawerItem("Dashboard", R.drawable.app)
        ReportsWithDropdown()
        DrawerItem("Sale List", R.drawable.app)
        DrawerItem("Purchase List", R.drawable.app)
        DrawerItem("Estimate List", R.drawable.app)
        DrawerItem("Expense List", R.drawable.app)
        DrawerItem("Money In List", R.drawable.app)
        DrawerItem("Money Out List", R.drawable.app)
        DrawerItem("Item List", R.drawable.app)
        DrawerItem("Party List", R.drawable.app)
        DrawerItem("Kot List", R.drawable.app)
        DrawerItem("Staff List", R.drawable.app)
        DrawerItem("Log Out", R.drawable.app)
    }
}

@Composable
fun DrawerHeader() {
    Box(modifier = Modifier.background(color = Color(0x33808080))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.app),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // User Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Kapil Kumar Singh",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "+91 8303083016",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.app), // Replace with your edit drawable
                    contentDescription = "Edit",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ReportsWithDropdown() {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // Reports List Item
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.app),
                contentDescription = "Reports",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Reports",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.W400,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.dropdown),
                contentDescription = "Dropdown",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Report 1") },
                onClick = { /* Handle Report 1 */ }
            )
            DropdownMenuItem(
                text = { Text("Report 2") },
                onClick = { /* Handle Report 2 */ }
            )
            DropdownMenuItem(
                text = { Text("Report 3") },
                onClick = { /* Handle Report 3 */ }
            )
        }
    }
}

@Composable
fun DrawerItem(
    label: String,
    iconRes: Int,
    color: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = iconRes), // Replace with your drawable
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = label,
            fontSize = 16.sp,
            color = color,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.W400,
            modifier = Modifier.weight(1f)
        )
    }
}

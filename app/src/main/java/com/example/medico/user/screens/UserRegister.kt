package com.example.medico.user.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.R
import com.example.medico.user.data.UserDetails
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.HeaderSection
import com.example.medico.common.utils.Tagline

@Composable
fun Register(
    navController: NavController,
    vm: AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var bloodGroup by remember { mutableStateOf("AB+") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var error: String? by remember { mutableStateOf(null) }

    Scaffold { paddingValues ->
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
                        text = "Register",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            // Card for the form
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.medium,
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(24.dp)) {

                                    // First and Last Name
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        CustomTextField(
                                            value = firstName,
                                            onValueChange = { firstName = it },
                                            label = "First Name",
                                            modifier = Modifier.weight(1f),

                                            )
                                        CustomTextField(
                                            value = lastName,
                                            onValueChange = { lastName = it },
                                            label = "Last Name",
                                            modifier = Modifier.weight(1f),

                                            )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Age
                                    CustomTextField(
                                        value = age,
                                        onValueChange = { age = it },
                                        label = "Age (in years)",
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardType = KeyboardType.Phone,

                                        )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Gender Dropdown
                                    GenderDropdown(
                                        selectedGender = gender,
                                        onGenderSelected = { newGender ->
                                            gender = newGender
                                        }
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Blood Group Dropdown
                                    BloodGroupDropdown(
                                        selectedBloodGroup = bloodGroup,
                                        onBloodGroupSelected = { newBloodGroup ->
                                            bloodGroup = newBloodGroup
                                        }
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Phone and Email
                                    CustomTextField(
                                        value = phone,
                                        onValueChange = { phone = it },
                                        label = "Phone",
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardType = KeyboardType.Phone,

                                        )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    CustomTextField(
                                        value = email,
                                        onValueChange = { email = it },
                                        label = "Email",
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardType = KeyboardType.Email,

                                        )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    CustomTextField(
                                        value = password,
                                        onValueChange = { password = it },
                                        label = "Password",
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardType = KeyboardType.Email,

                                        )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    // Register Button
                                    Button(
                                        onClick = {
                                            val user = UserDetails(
                                                firstName,
                                                lastName,
                                                age,
                                                gender,
                                                bloodGroup,
                                                phone,
                                                email,
                                                password
                                            )
                                            vm.register(
                                                user,
                                                onSuccess = {
                                                    Toast.makeText(
                                                        context,
                                                        "Registration Successful\nLog in to continue",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    navController.navigate(Routes.UserLogin.routes) {
                                                        popUpTo(0) {
                                                            inclusive = true
                                                        }
                                                    }
                                                },
                                                onError = { error = it }
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(
                                            text = "Register",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BloodGroupDropdown(
    selectedBloodGroup: String,
    onBloodGroupSelected: (String) -> Unit,
) {
    val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    CommonDropDownMenu(
        items = bloodGroups,
        selectedItem = selectedBloodGroup,
        onItemSelected = { bloodGroup ->
            onBloodGroupSelected(bloodGroup)
        },
        label = "Blood Group"
    )
}

@Composable
fun GenderDropdown(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
) {
    val genderOptions = listOf("Male", "Female", "Other")
    CommonDropDownMenu(
        items = genderOptions,
        selectedItem = selectedGender,
        onItemSelected = { gender ->
            onGenderSelected(gender)  // Notify the parent composable to update the ViewModel
        },
        label = "Gender"
    )
}

@Composable
fun CommonDropDownMenu(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    label: String,
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isTextFieldEditable by remember { mutableStateOf(false) } // Track if the TextField is editable

    // Filter items based on searchText
    val filteredItems = if (searchText.isEmpty()) {
        items
    } else {
        items.filter { it.contains(searchText, ignoreCase = true) }
    }

    Box {
        CustomTextField(
            value = selectedItem,
            onValueChange = {}, // Don't allow typing, only selecting
            label = label,
            readOnly = !isTextFieldEditable, // Make it editable only when the dropdown is open
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true; isTextFieldEditable = true }, // Open dropdown and enable typing
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    tint = Color.Black,
                    modifier = Modifier.clickable { expanded = true; isTextFieldEditable = true }
                )
            }
        )

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                isTextFieldEditable = false // Disable editing after dropdown closes
            }
        ) {
            // Add a TextField for search input
            DropdownMenuItem(
                text = {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text(text = "Search...", color = Color.White) },
                        textStyle = TextStyle(color = Color.White),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                },
                onClick = {} // No action when clicking on the search field
            )

            // Display filtered items
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            style = TextStyle(color = Color.White)
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                        searchText = "" // Clear search text after selection
                        expanded = false
                        isTextFieldEditable = false // Disable editing once an item is selected
                    }
                )
            }
        }
    }
}



@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    trailingIcon: @Composable() (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        readOnly = readOnly,
        modifier = modifier,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        trailingIcon = trailingIcon,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        ),
    )
}


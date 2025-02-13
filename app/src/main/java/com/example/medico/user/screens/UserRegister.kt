package com.example.medico.user.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medico.common.navigation.Routes
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.BloodGroupDropdown
import com.example.medico.common.utils.CustomTextField
import com.example.medico.common.utils.GenderDropdown
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.data.UserDetails

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
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 32.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Column(modifier = Modifier.padding(24.dp)) {

                            // First & Last Name
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CustomTextField(
                                    value = firstName,
                                    onValueChange = { firstName = it },
                                    label = "First Name",
                                    modifier = Modifier.weight(1f)
                                )
                                CustomTextField(
                                    value = lastName,
                                    onValueChange = { lastName = it },
                                    label = "Last Name",
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Age
                            CustomTextField(
                                value = age,
                                onValueChange = { age = it },
                                label = "Age (in years)",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Number
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Gender Dropdown
                            GenderDropdown(
                                selectedGender = gender,
                                onGenderSelected = { gender = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Blood Group Dropdown
                            BloodGroupDropdown(
                                selectedBloodGroup = bloodGroup,
                                onBloodGroupSelected = { bloodGroup = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Phone
                            CustomTextField(
                                value = phone,
                                onValueChange = { phone = it },
                                label = "Phone",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Phone
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Email
                            CustomTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = "Email",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Email
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Password
                            CustomTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = "Password",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Password
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Display Error Message (if any)
                            errorMessage?.let {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            // Register Button
                            Button(
                                onClick = {
                                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank() ||
                                        phone.isBlank() || password.isBlank() || age.isBlank()
                                    ) {
                                        errorMessage = "All fields are required!"
                                        return@Button
                                    }
                                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                        errorMessage = "Invalid email format!"
                                        return@Button
                                    }
                                    if (phone.length < 10) {
                                        errorMessage = "Phone number must be at least 10 digits!"
                                        return@Button
                                    }

                                    // Clear previous errors
                                    errorMessage = null

                                    val user = UserDetails(
                                        firstName, lastName, age, gender, bloodGroup, phone, email, password
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
                                                popUpTo(0) { inclusive = true }
                                            }
                                        },
                                        onError = { errorMessage = it }
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text(
                                    text = "Register",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

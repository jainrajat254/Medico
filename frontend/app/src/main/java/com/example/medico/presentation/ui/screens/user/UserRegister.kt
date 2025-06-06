package com.example.medico.presentation.ui.screens.user

import android.util.Patterns
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.medico.domain.model.UserDetails
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.BloodGroupDropdown
import com.example.medico.presentation.ui.screens.CustomTextField
import com.example.medico.presentation.ui.screens.GenderDropdown
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.utils.ResultState

@Composable
fun UserRegister(
    navController: NavController,
    authViewModel: com.example.medico.presentation.viewmodel.AuthViewModel,
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var bloodGroup by remember { mutableStateOf("AB+") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val userRegisterState by authViewModel.userRegisterState.collectAsState()
    var isDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(userRegisterState) {
        when (userRegisterState) {
            is ResultState.Success -> {
                isDialog = false
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.UserLogin.routes) {
                    popUpTo(0) { inclusive = true }
                }
            }

            is ResultState.Error -> {
                isDialog = false
                val message = (userRegisterState as ResultState.Error).error
                Toast.makeText(context, message.toString(), Toast.LENGTH_LONG).show()
            }

            is ResultState.Loading -> isDialog = true
            is ResultState.Idle -> isDialog = false
        }
    }

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

                            CustomTextField(
                                value = age,
                                onValueChange = { age = it },
                                label = "Age (in years)",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Number
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            GenderDropdown(
                                selectedGender = gender,
                                onGenderSelected = { gender = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            BloodGroupDropdown(
                                selectedBloodGroup = bloodGroup,
                                onBloodGroupSelected = { bloodGroup = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            CustomTextField(
                                value = phone,
                                onValueChange = { phone = it },
                                label = "Phone",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Phone
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            CustomTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = "Email",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Email
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            CustomTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = "Password",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Password
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            errorMessage?.let {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            Button(
                                onClick = {
                                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank() ||
                                        phone.isBlank() || password.isBlank() || age.isBlank()
                                    ) {
                                        errorMessage = "All fields are required!"
                                        return@Button
                                    }
                                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                        errorMessage = "Invalid email format!"
                                        return@Button
                                    }
                                    if (phone.length < 10) {
                                        errorMessage = "Phone number must be at least 10 digits!"
                                        return@Button
                                    }

                                    errorMessage = null

                                    val user = UserDetails(
                                        firstName = firstName,
                                        lastName = lastName,
                                        age = age,
                                        gender = gender,
                                        bloodGroup = bloodGroup,
                                        phone = phone,
                                        email = email,
                                        password = password
                                    )

                                    authViewModel.registerUser(user)
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

                if (isDialog) {
                    CustomLoader()
                }
            }
        }
    }
}


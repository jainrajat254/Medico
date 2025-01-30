package com.example.medico.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.medico.R
import com.example.medico.controllers.MyBottomBar
import com.example.medico.data.BloodGroup
import com.example.medico.data.Gender
import com.example.medico.models.AuthState
import com.example.medico.models.AuthViewModel
import com.example.medico.models.RegisterViewModel

@Composable
fun Register(navController: NavHostController? = null) {
    val context = LocalContext.current
    val viewModel: RegisterViewModel = viewModel()

    val authViewModel: AuthViewModel = viewModel()
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController?.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

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
                                            value = viewModel.firstName.value,
                                            onValueChange = { viewModel.firstName.value = it },
                                            label = "First Name",
                                            modifier = Modifier.weight(1f)
                                        )
                                        CustomTextField(
                                            value = viewModel.lastName.value,
                                            onValueChange = { viewModel.lastName.value = it },
                                            label = "Last Name",
                                            modifier = Modifier.weight(1f)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Age
                                    CustomTextField(
                                        value = viewModel.age.value,
                                        onValueChange = { viewModel.age.value = it },
                                        label = "Age (in years)",
                                        keyboardType = KeyboardType.Phone,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Gender Dropdown
                                    GenderDropdown(viewModel)

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Weight and Height
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        CustomTextField(
                                            value = viewModel.weight.value,
                                            onValueChange = { viewModel.weight.value = it },
                                            label = "Weight (kg)",
                                            keyboardType = KeyboardType.Number,
                                            modifier = Modifier.weight(1f)
                                        )
                                        CustomTextField(
                                            value = viewModel.height.value,
                                            onValueChange = { viewModel.height.value = it },
                                            label = "Height (cm)",
                                            keyboardType = KeyboardType.Number,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Blood Group Dropdown
                                    BloodGroupDropdown(viewModel)

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Phone and Email
                                    CustomTextField(
                                        value = viewModel.phone.value,
                                        onValueChange = { viewModel.phone.value = it },
                                        label = "Phone",
                                        keyboardType = KeyboardType.Phone,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    CustomTextField(
                                        value = viewModel.email.value,
                                        onValueChange = { viewModel.email.value = it },
                                        label = "Email",
                                        keyboardType = KeyboardType.Email,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Password Field
                                    PasswordField(viewModel)

                                    Spacer(modifier = Modifier.height(24.dp))

                                    // Register Button
                                    Button(
                                        onClick = {
                                            viewModel.onRegisterClicked(
                                                navController,
                                                context,
                                                authViewModel
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
fun BloodGroupDropdown(viewModel: RegisterViewModel) {
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = viewModel.bloodGroup.value?.group ?: "",
            onValueChange = { },
            label = { Text("Blood Group") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.bloodGroupExpanded.value = !viewModel.bloodGroupExpanded.value
                }) {
                    Icon(
                        imageVector = if (viewModel.bloodGroupExpanded.value) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        DropdownMenu(
            expanded = viewModel.bloodGroupExpanded.value,
            onDismissRequest = { viewModel.bloodGroupExpanded.value = false }
        ) {
            BloodGroup.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.group) },
                    onClick = {
                        viewModel.bloodGroup.value = option
                        viewModel.bloodGroupExpanded.value = false
                    }
                )
            }
        }
    }
}


@Composable
fun GenderDropdown(viewModel: RegisterViewModel) {
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = viewModel.gender.value?.displayName ?: "",
            onValueChange = { },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { viewModel.expanded.value = !viewModel.expanded.value }) {
                    Icon(
                        imageVector = if (viewModel.expanded.value) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        DropdownMenu(
            expanded = viewModel.expanded.value,
            onDismissRequest = { viewModel.expanded.value = false }
        ) {
            Gender.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.displayName) },
                    onClick = {
                        viewModel.gender.value = option
                        viewModel.expanded.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun PasswordField(viewModel: RegisterViewModel) {
    OutlinedTextField(
        value = viewModel.password.value,
        onValueChange = { viewModel.password.value = it },
        label = { Text("Password") },
        visualTransformation = if (viewModel.isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                viewModel.isPasswordVisible.value = !viewModel.isPasswordVisible.value
            }) {
                Icon(
                    imageVector = if (viewModel.isPasswordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = null
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
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
            fontWeight = FontWeight.SemiBold
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun RegisterPreview() {
    Register()
}

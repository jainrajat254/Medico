package com.example.medico.pages

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.medico.controllers.Routes
import com.example.medico.data.UserData
import com.example.medico.models.AuthViewModel

@Composable
fun Register(navController: NavController, vm: AuthViewModel) {

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
                                        keyboardType = KeyboardType.Phone,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Gender Dropdown
                                    GenderDropDownMenu(
                                        gender = gender,
                                        onGenderSelected = { newGender ->
                                            gender = newGender
                                        }
                                    )

                                    // Weight and Height
//                                    Row(
//                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                        modifier = Modifier.fillMaxWidth()
//                                    ) {
//                                        CustomTextField(
//                                            value = viewModel.weight.value,
//                                            onValueChange = { viewModel.weight.value = it },
//                                            label = "Weight (kg)",
//                                            keyboardType = KeyboardType.Number,
//                                            modifier = Modifier.weight(1f)
//                                        )
//                                        CustomTextField(
//                                            value = viewModel.height.value,
//                                            onValueChange = { viewModel.height.value = it },
//                                            label = "Height (cm)",
//                                            keyboardType = KeyboardType.Number,
//                                            modifier = Modifier.weight(1f)
//                                        )
//                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Blood Group Dropdown
                                    BloodGroupDropDownMenu(
                                        bloodGroup = bloodGroup,
                                        onBloodGroupSelected = { blood ->
                                            bloodGroup = blood
                                        }
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Phone and Email
                                    CustomTextField(
                                        value = phone,
                                        onValueChange = { phone = it },
                                        label = "Phone",
                                        keyboardType = KeyboardType.Phone,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    CustomTextField(
                                        value = email,
                                        onValueChange = { email = it },
                                        label = "Email",
                                        keyboardType = KeyboardType.Email,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    CustomTextField(
                                        value = password,
                                        onValueChange = { password = it },
                                        label = "Password",
                                        keyboardType = KeyboardType.Email,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    // Register Button
                                    Button(
                                        onClick = {
                                            val user = UserData(
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
                                                    Toast.makeText(context,"Registration Successful\nLog in to continue",
                                                        Toast.LENGTH_SHORT).show()
                                                    navController.navigate(Routes.Login.routes) {
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
fun GenderDropDownMenu(
    modifier: Modifier = Modifier,
    gender: String,
    onGenderSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = gender,
            onValueChange = { },
            label = {
                Text(
                    text = "Gender",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    ),
                    color = Color.Gray
                )
            },
            readOnly = true,
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .width(300.dp)
                .padding(top = 10.dp)
                .clickable { expanded = true },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.clickable { expanded = true }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Male") },
                onClick = {
                    onGenderSelected("Male")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Female") },
                onClick = {
                    onGenderSelected("Female")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Others") },
                onClick = {
                    onGenderSelected("Others")
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun BloodGroupDropDownMenu(
    modifier: Modifier = Modifier,
    bloodGroup: String,
    onBloodGroupSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = bloodGroup,
            onValueChange = { },
            label = {
                Text(
                    text = "Blood Group",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    ),
                    color = Color.Gray
                )
            },
            readOnly = true,
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .width(300.dp)
                .padding(top = 10.dp)
                .clickable { expanded = true },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.clickable { expanded = true }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("A+") },
                onClick = {
                    onBloodGroupSelected("A+")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("A-") },
                onClick = {
                    onBloodGroupSelected("A-")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("B+") },
                onClick = {
                    onBloodGroupSelected("B+")
                    expanded = false
                }
            )
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

//@Preview(showBackground = true)
//@Composable
//private fun RegisterPreview() {
//    Register(vm = vm)
//}

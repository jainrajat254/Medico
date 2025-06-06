package com.example.medico.presentation.ui.screens.doctor

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.domain.model.DoctorDetails
import com.example.medico.presentation.ui.navigation.Routes
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.CustomTextField
import com.example.medico.presentation.ui.screens.GenderDropdown
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.utils.ResultState

@Composable
fun DoctorRegister(
    navController: NavController,
    authViewModel: com.example.medico.presentation.viewmodel.AuthViewModel,
) {
    val context = LocalContext.current
    val docRegisterState by authViewModel.docRegisterState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 4 })

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var uid by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    var workspaceName by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var medicalRegNo by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var specialization by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var workingTime by remember { mutableStateOf("") }
    var fee by remember { mutableStateOf("") }

    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var availableForOnlineConsultation by remember { mutableStateOf("0") }

    var isDialog by remember {
        mutableStateOf(false)
    }

    // Observe registration result
    LaunchedEffect(docRegisterState) {
        when (docRegisterState) {
            is ResultState.Success -> {
                isDialog = false
                Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.DoctorLogin.routes) {
                    popUpTo(0) { inclusive = true }
                }
            }

            is ResultState.Error -> {
                isDialog = false
                Toast.makeText(
                    context,
                    (docRegisterState as ResultState.Error).error.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

            is ResultState.Loading -> isDialog = true
            is ResultState.Idle -> isDialog = false
        }
    }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 32.dp),
                    textAlign = TextAlign.Center
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                        ) {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxSize()
                            ) { page ->
                                when (page) {
                                    0 -> {
                                        PersonalInfoPage(
                                            firstName, { firstName = it },
                                            lastName, { lastName = it },
                                            uid, { uid = it },
                                            dob, { dob = it },
                                            gender, { gender = it }
                                        )
                                    }

                                    1 -> {
                                        MedicalInfoPage(
                                            medicalRegNo, { medicalRegNo = it },
                                            qualification, { qualification = it },
                                            specialization, { specialization = it },
                                            experience, { experience = it },
                                            workingTime, { workingTime = it },
                                            fee, { fee = it }
                                        )
                                    }

                                    2 -> {
                                        AddressPage(
                                            workspaceName, { workspaceName = it },
                                            state, { state = it },
                                            district, { district = it },
                                            zipCode, { zipCode = it },
                                            address, { address = it }
                                        )
                                    }

                                    3 -> {
                                        FinalPage(
                                            phone,
                                            { phone = it },
                                            email,
                                            { email = it },
                                            password,
                                            { password = it },
                                            availableForOnlineConsultation,
                                            { availableForOnlineConsultation = it },
                                            onRegister = {
                                                val doctor = DoctorDetails(
                                                    address = address,
                                                    availableForOnlineConsultation = availableForOnlineConsultation.toIntOrNull() == 1,
                                                    district = district,
                                                    dob = dob,
                                                    email = email,
                                                    experience = experience.toIntOrNull() ?: 0,
                                                    fee = fee.toIntOrNull() ?: 0,
                                                    firstName = firstName,
                                                    gender = gender,
                                                    lastName = lastName,
                                                    medicalRegNo = medicalRegNo,
                                                    password = password,
                                                    phone = phone,
                                                    qualification = qualification,
                                                    specialization = specialization,
                                                    state = state,
                                                    uid = uid,
                                                    workingTime = workingTime.split(",")
                                                        .map { it.trim() },
                                                    workspaceName = workspaceName,
                                                    zipCode = zipCode
                                                )

                                                authViewModel.registerDoc(doctor)
                                            }

                                        )
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        PagerIndicator(pagerState)
                    }
                }

            }
        }
        if (isDialog) {
            CustomLoader()
        }
    }
}

@Composable
fun PersonalInfoPage(
    firstName: String, onFirstNameChange: (String) -> Unit,
    lastName: String, onLastNameChange: (String) -> Unit,
    uid: String, onUidChange: (String) -> Unit,
    dob: String, onDobChange: (String) -> Unit,
    gender: String, onGenderChange: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                "Personal Info",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        item {
            CustomTextField(firstName, onFirstNameChange, "First Name", Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            CustomTextField(lastName, onLastNameChange, "Last Name", Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                uid,
                onUidChange,
                "Aadhar Number",
                Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(dob, onDobChange, "Date of Birth", Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            GenderDropdown(selectedGender = gender, onGenderSelected = onGenderChange)
        }
    }
}

@Composable
fun MedicalInfoPage(
    medicalRegNo: String, onMedicalRegNoChange: (String) -> Unit,
    qualification: String, onQualificationChange: (String) -> Unit,
    specialization: String, onSpecializationChange: (String) -> Unit,
    experience: String, onExperienceChange: (String) -> Unit,
    workingTime: String, onWorkingTimeChange: (String) -> Unit,
    fee: String, onFeeChange: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                "Medical Info",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        item {
            CustomTextField(
                medicalRegNo,
                onMedicalRegNoChange,
                "Medical Reg. Number",
                Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                qualification,
                onQualificationChange,
                "Qualification",
                Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                specialization,
                onSpecializationChange,
                "Specialization",
                Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                experience,
                onExperienceChange,
                "Experience (Years)",
                Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                workingTime,
                onWorkingTimeChange,
                "Working Time (e.g. 10am-4pm)",
                Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                fee,
                onFeeChange,
                "Consultation Fee",
                Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )
        }
    }
}

@Composable
fun AddressPage(
    workspaceName: String, onWorkspaceNameChange: (String) -> Unit,
    state: String, onStateChange: (String) -> Unit,
    district: String, onDistrictChange: (String) -> Unit,
    zipCode: String, onZipCodeChange: (String) -> Unit,
    address: String, onAddressChange: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                "Address Info",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        item {
            CustomTextField(
                workspaceName,
                onWorkspaceNameChange,
                "Workspace Name",
                Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            // Replace with dropdown if needed
            CustomTextField(state, onStateChange, "State", Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            CustomTextField(district, onDistrictChange, "District", Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                zipCode,
                onZipCodeChange,
                "Zip Code",
                Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(address, onAddressChange, "Address", Modifier.fillMaxWidth())
        }
    }
}


@Composable
fun FinalPage(
    phone: String, onPhoneChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    password: String, onPasswordChange: (String) -> Unit,
    available: String, onAvailableChange: (String) -> Unit,
    onRegister: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                "Account Info",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        item {
            CustomTextField(
                phone,
                onPhoneChange,
                "Phone Number",
                Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Phone
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                email,
                onEmailChange,
                "Email Address",
                Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                password,
                onPasswordChange,
                "Password",
                Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password
            )
            Spacer(Modifier.height(16.dp))
            CustomTextField(
                available,
                onAvailableChange,
                "Online Consult? (1/0)",
                Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onRegister,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Register", fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun PagerIndicator(pagerState: PagerState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { page ->
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .padding(2.dp)
                    .background(
                        if (pagerState.currentPage == page) Color.Black else Color.Gray,
                        shape = CircleShape
                    )
            )
        }
    }
}

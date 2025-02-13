package com.example.medico.doctor.screens

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
import androidx.compose.runtime.collectAsState
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
import com.example.medico.common.navigation.Routes
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.CustomTextField
import com.example.medico.common.utils.GenderDropdown
import com.example.medico.common.utils.StateDistrictDropdown
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.doctor.model.DoctorDetails
import com.example.medico.doctor.viewModel.DoctorRegister
import org.koin.androidx.compose.getViewModel

@Composable
fun DoctorRegister(navController: NavController, vm: AuthViewModel) {
    val viewModel: DoctorRegister = getViewModel()
    val pagerState = rememberPagerState(pageCount = { 4 })

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ✅ "Register" text is now OUTSIDE LazyColumn
                Text(
                    text = "Register",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            bottom = 32.dp
                        ) // Top padding to separate it from status bar
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        // ✅ Wrap the Pager in a Box with a Fixed Height
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)  // Adjust height as needed
                        ) {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxSize()
                            ) { page ->
                                when (page) {
                                    0 -> PersonalInfo(viewModel)
                                    1 -> MedicalInfoScreen(viewModel)
                                    2 -> AddressScreen(viewModel)
                                    3 -> MedicalAddressScreen(viewModel, vm, navController)
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        PagerIndicator(pagerState) // ✅ Now it will be visible
                    }
                }
            }
        }
    }
}


@Composable
fun PersonalInfo(viewModel: DoctorRegister) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            // Title
            Text(
                text = "Professional Details",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.surfaceContainer,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
        item {
            CustomTextField(
                value = viewModel.firstName.collectAsState().value,
                onValueChange = viewModel::updateFirstName,
                label = "First Name",
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CustomTextField(
                value = viewModel.lastName.collectAsState().value,
                onValueChange = viewModel::updateLastName,
                label = "Last Name",
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            CustomTextField(
                value = viewModel.uid.collectAsState().value,
                onValueChange = { viewModel.updateUid(it) },
                label = "Aadhar Number",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CustomTextField(
                value = viewModel.dob.collectAsState().value,
                onValueChange = viewModel::updateDob,
                label = "Date of Birth",
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            GenderDropdown(
                selectedGender = viewModel.gender.collectAsState().value,
                onGenderSelected = { newGender ->
                    viewModel.updateGender(newGender)  // Update the gender in the ViewModel
                }
            )
        }
    }
}

@Composable
fun AddressScreen(viewModel: DoctorRegister) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            // Title
            Text(
                text = "Address Information",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.surfaceContainer,
                modifier = Modifier

                    .padding(bottom = 32.dp)
            )
        }
        item {
            CustomTextField(
                value = viewModel.workspaceName.collectAsState().value,
                onValueChange = viewModel::updateWorkspaceName,
                label = "Workspace Name",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            StateDistrictDropdown(
                viewModel = viewModel,
                onDistrictSelected = { district ->
                    viewModel.updateDistrict(district)
                }
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CustomTextField(
                value = viewModel.zipCode.collectAsState().value,
                onValueChange = { viewModel.updateZipCode(it) },
                label = "Zip Code",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CustomTextField(
                value = viewModel.address.collectAsState().value,
                onValueChange = viewModel::updateAddress,
                label = "Address",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MedicalInfoScreen(viewModel: DoctorRegister) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            // Title
            Text(
                text = "Medical Profile",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.surfaceContainer,
                modifier = Modifier

                    .padding(bottom = 32.dp)
            )
        }
        item {
            CustomTextField(
                value = viewModel.medicalRegNo.collectAsState().value,
                onValueChange = viewModel::updateMedicalRegNo,
                label = "Medical Registration Number",
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CustomTextField(
                value = viewModel.qualification.collectAsState().value,
                onValueChange = viewModel::updateQualification,
                label = "Qualification",
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CustomTextField(
                value = viewModel.specialization.collectAsState().value,
                onValueChange = viewModel::updateSpecialization,
                label = "Specialization",
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CustomTextField(
                value = viewModel.experience.collectAsState().value.toString(),
                onValueChange = { viewModel.updateExperience(it) },
                label = "Experience (Years)",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )

        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CustomTextField(
                value = viewModel.workingTime.collectAsState().value.joinToString(", "),
                onValueChange = { newTime ->
                    viewModel.updateWorkingTime(newTime)
                },
                label = "Working Time",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Text
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            CustomTextField(
                value = viewModel.fee.collectAsState().value.toString(),
                onValueChange = { viewModel.updateFee(it) },
                label = "Consultation Fee",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )
        }
    }
}

@Composable
fun MedicalAddressScreen(
    viewModel: DoctorRegister,
    vm: AuthViewModel,
    navController: NavController,
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Add spacing
        ) {
            item {
                // Title
                Text(
                    text = "Work Information",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            item {
                CustomTextField(
                    value = viewModel.phone.collectAsState().value,
                    onValueChange = { viewModel.updatePhone(it) },
                    label = "Phone Number",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Phone
                )
            }

            item {
                CustomTextField(
                    value = viewModel.email.collectAsState().value,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = "Email Address",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Email
                )
            }

            item {
                CustomTextField(
                    value = viewModel.password.collectAsState().value,
                    onValueChange = viewModel::updatePassword,
                    label = "Password",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Password
                )
            }

            item {
                CustomTextField(
                    value = viewModel.availableForOnlineConsultation.collectAsState().value.toString(),
                    onValueChange = viewModel::updateAvailableForOnlineConsultation,
                    label = "Online Consultation Availability",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Number
                )
            }

            item {
                Button(
                    onClick = {
                        val error = viewModel.isFormValid()
                        if (error != null) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        } else {
                            val doctor = DoctorDetails(
                                firstName = viewModel.firstName.value,
                                lastName = viewModel.lastName.value,
                                uid = viewModel.uid.value,
                                dob = viewModel.dob.value,
                                gender = viewModel.gender.value.toString(),
                                workspaceName = viewModel.workspaceName.value,
                                state = viewModel.state.toString(),
                                district = viewModel.district.value,
                                zipCode = viewModel.zipCode.value,
                                address = viewModel.address.value,
                                medicalRegNo = viewModel.medicalRegNo.value,
                                qualification = viewModel.qualification.value,
                                specialization = viewModel.specialization.value,
                                experience = viewModel.experience.value,
                                workingTime = viewModel.workingTime.value,
                                fee = viewModel.fee.value,
                                phone = viewModel.phone.value,
                                email = viewModel.email.value,
                                password = viewModel.password.value,
                                availableForOnlineConsultation = viewModel.availableForOnlineConsultation.value
                            )
                            vm.register(doctor, onSuccess = {
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
                                onError = { error }
                            )
                        }
                    }, modifier = Modifier.fillMaxWidth(),
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

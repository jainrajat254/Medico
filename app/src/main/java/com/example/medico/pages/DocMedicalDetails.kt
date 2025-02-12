package com.example.medico.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medico.R
import com.example.medico.data.DocMedicalDetailsDTO
import com.example.medico.models.AuthViewModel
import com.example.medico.models.DocAccountViewModel
import com.example.medico.sharedPreferences.SharedPreferencesManager
import org.koin.androidx.compose.getViewModel

@Composable
fun DocMedicalDetails(
    sharedPreferencesManager: SharedPreferencesManager,
    vm: AuthViewModel,
    navController: NavController,
) {
    val docAccountViewModel: DocAccountViewModel = getViewModel()
    val context = LocalContext.current

    val isEditing by docAccountViewModel.isEditing.collectAsState()
    val id by docAccountViewModel.id.collectAsState()
    val medicalRegNo by docAccountViewModel.medicalRegNo.collectAsState()
    val specialization by docAccountViewModel.specialization.collectAsState()
    val qualification by docAccountViewModel.qualification.collectAsState()
    val fee by docAccountViewModel.fee.collectAsState()
    val experience by docAccountViewModel.experience.collectAsState()
    val availableForOnlineConsultation by docAccountViewModel.availableForOnlineConsultation.collectAsState()

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_app),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                HeaderSection()
                Spacer(modifier = Modifier.height(16.dp))
                Tagline()
                Spacer(modifier = Modifier.height(32.dp))

                // Lazy Column for User Info Fields
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        UserInfoField(
                            label = "Medical Registration Number",
                            value = medicalRegNo,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateMedicalRegNo,
                        )
                        UserInfoField(
                            label = "Qualification",
                            value = qualification,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateQualification,
                        )
                        UserInfoField(
                            label = "Specialization",
                            value = specialization,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateSpecialization
                        )
                        UserInfoField(
                            label = "Experience",
                            value = experience.toString(),
                            isEditing = isEditing,
                            onValueChange = { newValue ->
                                val intValue = newValue.toIntOrNull() ?: experience
                                docAccountViewModel.updateExperience(intValue)
                            }
                        )
                        UserInfoField(
                            label = "Fee",
                            value = fee.toString(),
                            isEditing = isEditing,
                            onValueChange = { newValue ->
                                val intValue = newValue.toIntOrNull() ?: fee
                                docAccountViewModel.updateFee(intValue)
                            }
                        )
                        UserInfoField(
                            label = "Available For Online Consultation",
                            value = availableForOnlineConsultation.toString(),
                            isEditing = isEditing,
                            onValueChange = { newValue ->
                                val booleanValue = newValue.toBooleanStrictOrNull() ?: availableForOnlineConsultation
                                docAccountViewModel.updateAvailableForOnlineConsultation(booleanValue)
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (isEditing) {
                                    val formError = docAccountViewModel.isPersonalDetailsFormValid()
                                    if (formError == null) {
                                        val data = DocMedicalDetailsDTO(
                                            medicalRegNo,
                                            qualification,
                                            specialization,
                                            experience,
                                            fee,
                                            availableForOnlineConsultation
                                        )
                                        vm.editDocMedicalDetails(
                                            data,
                                            id,
                                            onSuccess = {
                                                Toast.makeText(
                                                    context,
                                                    "Details Updated",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                sharedPreferencesManager.editDocMedicalDetails(
                                                    medicalRegNo,
                                                    qualification,
                                                    specialization,
                                                    experience,
                                                    fee,
                                                    availableForOnlineConsultation
                                                )
                                                Log.d("data", "$data  $id")
                                            },
                                            onError = { errorMessage ->
                                                Log.e("EditDetails", errorMessage)
                                                Toast.makeText(
                                                    context,
                                                    "Error: $errorMessage. Please try again.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        )
                                        docAccountViewModel.toggleEditing()
                                    } else {
                                        Toast.makeText(context, formError, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                } else {
                                    docAccountViewModel.toggleEditing()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isEditing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = if (isEditing) "Save Changes" else "Edit Profile")
                        }
                    }
                }
            }
        }
    }
}
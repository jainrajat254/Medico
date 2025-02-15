package com.example.medico.user.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.UserInfoField
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.model.ExtraDetails
import com.example.medico.user.viewModel.UserDetails
import org.koin.androidx.compose.getViewModel

@Composable
fun InsuranceDetails(
    vm: AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val userDetails: UserDetails = getViewModel()

    val context = LocalContext.current
    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            val isEditing by userDetails.isEditing.collectAsState()
            val id by userDetails.id.collectAsState()
            val insuranceProvider by userDetails.insuranceProvider.collectAsState()
            val policyNumber by userDetails.policyNumber.collectAsState()
            val groupNumber by userDetails.groupNumber.collectAsState()
            val coverageDetails by userDetails.coverageDetails.collectAsState()

            var personalInfoId by rememberSaveable { mutableStateOf("") }

            LaunchedEffect(Unit) {
                vm.getExtraDetails(
                    userId = id,
                    onSuccess = { userDetailsResponse ->
                        Log.d("GetExtraDetails", "Fetched details: $userDetailsResponse")

                        userDetails.updateInsuranceProvider(userDetailsResponse.insuranceProvider)
                        userDetails.updatePolicyNumber(userDetailsResponse.policyNumber)
                        userDetails.updateGroupNumber(userDetailsResponse.groupNumber)
                        userDetails.updateCoverageDetails(userDetailsResponse.coverageDetails)

                        sharedPreferencesManager.saveUserDetails(userDetailsResponse)
                    },
                    onError = { error ->
                        Log.e("GetExtraDetails", "Error: $error")
                        Toast.makeText(
                            context,
                            "Error fetching details. Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )

                // Then fetch personalInfoId
                vm.getPersonalInfoId(id,
                    onResult = { uuid ->
                        personalInfoId = uuid
                        Log.d("Id", "Fetched personalInfoId: $personalInfoId")
                    },
                    onError = { error ->
                        Log.e("Error", error)
                    }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    UserInfoField(
                        label = "Insurance Provider",
                        value = insuranceProvider,
                        isEditing = isEditing,
                        onValueChange = userDetails::updateInsuranceProvider
                    )
                    UserInfoField(
                        label = "Policy Number",
                        value = policyNumber,
                        isEditing = isEditing,
                        onValueChange = userDetails::updatePolicyNumber
                    )
                    UserInfoField(
                        label = "Group Number",
                        value = groupNumber,
                        isEditing = isEditing,
                        onValueChange = userDetails::updateGroupNumber
                    )
                    UserInfoField(
                        label = "Coverage Details",
                        value = coverageDetails,
                        isEditing = isEditing,
                        onValueChange = userDetails::updateCoverageDetails
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (isEditing) {
                                val data = ExtraDetails(
                                    insuranceProvider = insuranceProvider,
                                    coverageDetails = coverageDetails,
                                    policyNumber = policyNumber,
                                    groupNumber = groupNumber
                                )
                                vm.addExtraDetails(
                                    data,
                                    personalInfoId,
                                    onSuccess = {
                                        Toast.makeText(
                                            context,
                                            "Details Updated",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val updatedUserDetails =
                                            sharedPreferencesManager.getUserDetails().copy(
                                                insuranceProvider = data.insuranceProvider,
                                                coverageDetails = data.coverageDetails,
                                                policyNumber = data.policyNumber,
                                                groupNumber = data.groupNumber
                                            )
                                        Log.d("data", "$updatedUserDetails  $personalInfoId")
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
                                userDetails.toggleEditing()
                            } else {
                                userDetails.toggleEditing()
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

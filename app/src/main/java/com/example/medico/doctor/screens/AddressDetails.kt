package com.example.medico.doctor.screens

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
import com.example.medico.R
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.HeaderSection
import com.example.medico.common.utils.Tagline
import com.example.medico.common.utils.UserInfoField
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.doctor.dto.EditDocAddressDetails
import com.example.medico.doctor.viewModel.DoctorDetails
import org.koin.androidx.compose.getViewModel

@Composable
fun DocAddressDetails(
    sharedPreferencesManager: SharedPreferencesManager,
    vm: AuthViewModel,
) {

    val doctorDetails: DoctorDetails = getViewModel()
    val context = LocalContext.current

    val isEditing by doctorDetails.isEditing.collectAsState()
    val id by doctorDetails.id.collectAsState()
    val address by doctorDetails.address.collectAsState()
    val state by doctorDetails.state.collectAsState()
    val district by doctorDetails.district.collectAsState()
    val zipCode by doctorDetails.zipCode.collectAsState()
    val workspaceName by doctorDetails.workspaceName.collectAsState()

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    UserInfoField(
                        label = "Workspace Name",
                        value = workspaceName,
                        isEditing = isEditing,
                        onValueChange = doctorDetails::updateWorkspaceName
                    )
                    UserInfoField(
                        label = "Address",
                        value = address,
                        isEditing = isEditing,
                        onValueChange = doctorDetails::updateAddress,
                    )
                    UserInfoField(
                        label = "State",
                        value = state,
                        isEditing = isEditing,
                        onValueChange = doctorDetails::updateState
                    )
                    UserInfoField(
                        label = "District",
                        value = district,
                        isEditing = isEditing,
                        onValueChange = doctorDetails::updateDistrict,
                    )
                    UserInfoField(
                        label = "Zip Code",
                        value = zipCode,
                        isEditing = isEditing,
                        onValueChange = doctorDetails::updateZipCode
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (isEditing) {
                                val formError = doctorDetails.isPersonalDetailsFormValid()
                                if (formError == null) {
                                    val data = EditDocAddressDetails(
                                        workspaceName,
                                        address,
                                        state,
                                        district,
                                        zipCode
                                    )
                                    vm.editDocAddressDetails(
                                        data,
                                        id,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "Details Updated",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            sharedPreferencesManager.editDocAddressDetails(
                                                workspaceName,
                                                address,
                                                state,
                                                district,
                                                zipCode
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
                                    doctorDetails.toggleEditing()
                                } else {
                                    Toast.makeText(context, formError, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                doctorDetails.toggleEditing()
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


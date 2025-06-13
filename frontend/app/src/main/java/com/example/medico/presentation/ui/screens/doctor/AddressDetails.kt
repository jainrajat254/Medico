package com.example.medico.presentation.ui.screens.doctor

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
import androidx.compose.ui.unit.dp
import com.example.medico.domain.model.EditDocAddressDetails
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.UserInfoField
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun DocAddressDetails(
    sharedPreferencesManager: SharedPreferencesManager,
    settingsViewModel: SettingsViewModel,
) {
    val context = LocalContext.current

    var workspaceName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    var isDialog by remember { mutableStateOf(false) }

    val docId = sharedPreferencesManager.getDocProfile()?.id ?: ""

    val editDocAddressState by settingsViewModel.editDocAddressState.collectAsState()

    LaunchedEffect(Unit) {
        sharedPreferencesManager.getDocProfile()?.let { profile ->
            workspaceName = profile.workspaceName
            address = profile.address
            state = profile.state
            district = profile.district
            zipCode = profile.zipCode
        }
    }

    LaunchedEffect(editDocAddressState) {
        when (val result = editDocAddressState) {
            is ResultState.Success -> {
                isDialog = false
                Toast.makeText(context, "Details Updated", Toast.LENGTH_SHORT).show()

                sharedPreferencesManager.getDocProfile()?.let { currentProfile ->
                    val updatedProfile = currentProfile.copy(
                        workspaceName = workspaceName,
                        address = address,
                        state = state,
                        district = district,
                        zipCode = zipCode
                    )
                    sharedPreferencesManager.saveDocProfile(updatedProfile)
                }
            }

            is ResultState.Error -> {
                isDialog = false
                Toast.makeText(
                    context,
                    "Error: ${result.error.message ?: "Unknown error"}. Please try again.",
                    Toast.LENGTH_LONG
                ).show()
            }

            ResultState.Loading -> isDialog = true
            ResultState.Idle -> isDialog = false
        }
    }

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
                        onValueChange = { workspaceName = it }
                    )
                    UserInfoField(
                        label = "Address",
                        value = address,
                        onValueChange = { address = it }
                    )
                    UserInfoField(
                        label = "State",
                        value = state,
                        onValueChange = { state = it }
                    )
                    UserInfoField(
                        label = "District",
                        value = district,
                        onValueChange = { district = it }
                    )
                    UserInfoField(
                        label = "Zip Code",
                        value = zipCode,
                        onValueChange = { zipCode = it }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (workspaceName.isBlank() || address.isBlank() || state.isBlank() || district.isBlank() || zipCode.isBlank()) {
                                Toast.makeText(
                                    context,
                                    "Please fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            val data = EditDocAddressDetails(
                                workspaceName,
                                address,
                                state,
                                district,
                                zipCode
                            )
                            settingsViewModel.editDocAddressDetails(data, id = docId)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors()
                    ) {
                        Text(text = "Save Changes")
                    }
                }
            }
        }
    }
}

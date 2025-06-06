package com.example.medico.presentation.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import com.example.medico.domain.model.ExtraDetails
import com.example.medico.presentation.ui.screens.UserInfoField
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun InsuranceDetails(
    settingsViewModel: SettingsViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val context = LocalContext.current
    val userId = sharedPreferencesManager.getUserProfile()?.id.orEmpty()

    val getExtraDetailsState by settingsViewModel.getExtraDetailsState.collectAsState()
    val addExtraDetailsState by settingsViewModel.addExtraDetailsState.collectAsState()

    var insuranceProvider by rememberSaveable { mutableStateOf("") }
    var policyNumber by rememberSaveable { mutableStateOf("") }
    var groupNumber by rememberSaveable { mutableStateOf("") }
    var coverageDetails by rememberSaveable { mutableStateOf("") }
    var isDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(userId) {
        val cached = sharedPreferencesManager.getUserExtraDetails()
        if (cached == null) {
            settingsViewModel.getExtraDetails(userId)
        } else {
            insuranceProvider = cached.insuranceProvider
            policyNumber = cached.policyNumber
            groupNumber = cached.groupNumber
            coverageDetails = cached.coverageDetails
        }

        settingsViewModel.getPersonalInfoId(userId)
    }

    LaunchedEffect(getExtraDetailsState) {
        val state = getExtraDetailsState
        if (state is ResultState.Success) {
            val details = state.data
            sharedPreferencesManager.saveUserExtraDetails(details)

            insuranceProvider = details.insuranceProvider
            policyNumber = details.policyNumber
            groupNumber = details.groupNumber
            coverageDetails = details.coverageDetails
        }
    }

    LaunchedEffect(addExtraDetailsState) {
        when (val result = addExtraDetailsState) {
            is ResultState.Success -> {
                isDialog = false
                val updated = result.data
                val current = sharedPreferencesManager.getUserExtraDetails()

                val merged = current?.copy(
                    insuranceProvider = updated.insuranceProvider,
                    policyNumber = updated.policyNumber,
                    groupNumber = updated.groupNumber,
                    coverageDetails = updated.coverageDetails
                ) ?: updated

                sharedPreferencesManager.saveUserExtraDetails(merged)
                Toast.makeText(context, "Insurance Details Updated", Toast.LENGTH_SHORT).show()
            }

            is ResultState.Error -> {
                isDialog = false
                Toast.makeText(context, "Failed to update insurance details", Toast.LENGTH_SHORT)
                    .show()
            }

            is ResultState.Idle -> isDialog = false
            is ResultState.Loading -> isDialog = true
        }
    }

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                UserInfoField(
                    label = "Insurance Provider",
                    value = insuranceProvider,
                    onValueChange = { insuranceProvider = it }
                )
                UserInfoField(
                    label = "Policy Number",
                    value = policyNumber,
                    onValueChange = { policyNumber = it }
                )
                UserInfoField(
                    label = "Group Number",
                    value = groupNumber,
                    onValueChange = { groupNumber = it }
                )
                UserInfoField(
                    label = "Coverage Details",
                    value = coverageDetails,
                    onValueChange = { coverageDetails = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val data = ExtraDetails(
                            insuranceProvider = insuranceProvider,
                            policyNumber = policyNumber,
                            groupNumber = groupNumber,
                            coverageDetails = coverageDetails
                        )
                        settingsViewModel.addExtraDetails(data, userId)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        }
    }

    if (isDialog) {
        CustomLoader()
    }
}


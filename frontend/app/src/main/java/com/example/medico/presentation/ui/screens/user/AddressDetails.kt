package com.example.medico.presentation.ui.screens.user

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
fun AddressDetails(
    settingsViewModel: SettingsViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val context = LocalContext.current
    val userId = sharedPreferencesManager.getUserProfile()?.id.orEmpty()

    // Collecting states from ViewModel
    val getExtraDetailsState by settingsViewModel.getExtraDetailsState.collectAsState()
    val addExtraDetailsState by settingsViewModel.addExtraDetailsState.collectAsState()
    val getPersonalInfoIdState by settingsViewModel.getPersonalInfoIdState.collectAsState()

    // UI states
    var state by rememberSaveable { mutableStateOf("") }
    var district by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var currentAddress by rememberSaveable { mutableStateOf("") }
    var permanentAddress by rememberSaveable { mutableStateOf("") }
    var isDialog by rememberSaveable { mutableStateOf(false) }
    var personalInfoId by rememberSaveable { mutableStateOf("") }

    // Fetch personalInfoId
    LaunchedEffect(Unit) {
        Log.d("AddressDetails", "Fetching personalInfoId for userId: $userId")
        settingsViewModel.getPersonalInfoId(userId)
    }

    // Handle personalInfoId state
    LaunchedEffect(getPersonalInfoIdState) {
        when (val result = getPersonalInfoIdState) {
            is ResultState.Success -> {
                val id = result.data.id
                Log.d("AddressDetails", "Received personalInfoId from ViewModel: $id")
                personalInfoId = id
                sharedPreferencesManager.saveUserExtraDetailsId(result.data)
            }

            is ResultState.Error -> {
                Log.e("AddressDetails", "Error getting personalInfoId: ${result.error.message}")
            }

            else -> Unit
        }
    }

    // Fetch extra details based on cache and ID
    LaunchedEffect(personalInfoId) {
        Log.d("AddressDetails", "LaunchedEffect triggered for personalInfoId: $personalInfoId")
        val cached = sharedPreferencesManager.getUserExtraDetails()

        if (cached == null || cached.id.isNullOrBlank()) {
            Log.d("AddressDetails", "No valid cached details found. Fetching from ViewModel.")
            settingsViewModel.getExtraDetails(userId)
        } else {
            Log.d("AddressDetails", "Loaded extra details from cache: $cached")
            state = cached.state
            district = cached.district
            city = cached.city
            currentAddress = cached.currentAddress
            permanentAddress = cached.permanentAddress
        }
    }

    // Update UI and cache when extra details are fetched
    LaunchedEffect(getExtraDetailsState) {
        when (val result = getExtraDetailsState) {
            is ResultState.Success -> {
                val details = result.data
                Log.d("AddressDetails", "Fetched extra details from backend: $details")
                sharedPreferencesManager.saveUserExtraDetails(details)

                state = details.state
                district = details.district
                city = details.city
                currentAddress = details.currentAddress
                permanentAddress = details.permanentAddress
            }

            is ResultState.Error -> {
                Log.e(
                    "AddressDetails",
                    "Failed to fetch extra details: ${result.error.message}"
                )
            }

            else -> Unit
        }
    }

    // Handle Add/Update extra details state
    LaunchedEffect(addExtraDetailsState) {
        when (val result = addExtraDetailsState) {
            is ResultState.Success -> {
                Log.d("AddressDetails", "Address details successfully updated: ${result.data}")
                isDialog = false

                val updated = result.data
                val current = sharedPreferencesManager.getUserExtraDetails()
                val merged = current?.copy(
                    state = updated.state,
                    district = updated.district,
                    city = updated.city,
                    currentAddress = updated.currentAddress,
                    permanentAddress = updated.permanentAddress
                ) ?: updated

                sharedPreferencesManager.saveUserExtraDetails(merged)
                Toast.makeText(context, "Address Details Updated", Toast.LENGTH_SHORT).show()
            }

            is ResultState.Error -> {
                Log.e("AddressDetails", "Error updating address: ${result.error.message}")
                isDialog = false
                Toast.makeText(context, "Error updating address", Toast.LENGTH_SHORT).show()
            }

            is ResultState.Loading -> {
                Log.d("AddressDetails", "Updating address details...")
                isDialog = true
            }

            is ResultState.Idle -> {
                isDialog = false
            }
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
                    label = "City",
                    value = city,
                    onValueChange = { city = it }
                )
                UserInfoField(
                    label = "Current Address",
                    value = currentAddress,
                    onValueChange = { currentAddress = it }
                )
                UserInfoField(
                    label = "Permanent Address",
                    value = permanentAddress,
                    onValueChange = { permanentAddress = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val data = ExtraDetails(
                            state = state,
                            district = district,
                            city = city,
                            currentAddress = currentAddress,
                            permanentAddress = permanentAddress
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

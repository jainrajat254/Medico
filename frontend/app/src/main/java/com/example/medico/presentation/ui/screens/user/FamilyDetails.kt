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
fun FamilyDetails(
    settingsViewModel: SettingsViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val userId = sharedPreferencesManager.getUserProfile()?.id.orEmpty()
    val context = LocalContext.current

    val getExtraDetailsState by settingsViewModel.getExtraDetailsState.collectAsState()
    val addExtraDetailsState by settingsViewModel.addExtraDetailsState.collectAsState()

    var emergencyName by rememberSaveable { mutableStateOf("") }
    var emergencyRelation by rememberSaveable { mutableStateOf("") }
    var emergencyPhone by rememberSaveable { mutableStateOf("") }
    var knownConditions by rememberSaveable { mutableStateOf("") }
    var medicalHistory by rememberSaveable { mutableStateOf("") }

    var isDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(userId) {
        val cachedDetails = sharedPreferencesManager.getUserExtraDetails()

        if (cachedDetails == null) {
            settingsViewModel.getExtraDetails(userId)
        } else {
            // ✅ Populate UI fields from cached data
            emergencyName = cachedDetails.emergencyContactName
            emergencyRelation = cachedDetails.emergencyContactRelation
            emergencyPhone = cachedDetails.emergencyContactPhone
            knownConditions = cachedDetails.knownHealthConditions
            medicalHistory = cachedDetails.familyMedicalHistory
        }

        settingsViewModel.getPersonalInfoId(userId)
    }

    LaunchedEffect(getExtraDetailsState) {
        val state = getExtraDetailsState
        if (state is ResultState.Success) {
            val details = state.data
            sharedPreferencesManager.saveUserExtraDetails(details)

            emergencyName = details.emergencyContactName
            emergencyRelation = details.emergencyContactRelation
            emergencyPhone = details.emergencyContactPhone
            knownConditions = details.knownHealthConditions
            medicalHistory = details.familyMedicalHistory
        }
    }

    LaunchedEffect(addExtraDetailsState) {
        when (val result = addExtraDetailsState) {
            is ResultState.Success -> {
                isDialog = false
                val updated = result.data
                val current = sharedPreferencesManager.getUserExtraDetails()

                // ✅ Merge updated values with existing ones to avoid overwriting
                val merged = current?.copy(
                    emergencyContactName = updated.emergencyContactName,
                    emergencyContactRelation = updated.emergencyContactRelation,
                    emergencyContactPhone = updated.emergencyContactPhone,
                    knownHealthConditions = updated.knownHealthConditions,
                    familyMedicalHistory = updated.familyMedicalHistory
                ) ?: updated

                sharedPreferencesManager.saveUserExtraDetails(merged)
                Toast.makeText(context, "Details Updated", Toast.LENGTH_SHORT).show()
            }

            is ResultState.Error -> {
                isDialog = false
                Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
            }

            is ResultState.Idle ->
                isDialog = false

            is ResultState.Loading ->
                isDialog = true
        }
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
                label = "Emergency Contact Name",
                value = emergencyName,
                onValueChange = { emergencyName = it }
            )
            UserInfoField(
                label = "Emergency Contact Relation",
                value = emergencyRelation,
                onValueChange = { emergencyRelation = it }
            )
            UserInfoField(
                label = "Emergency Contact Phone",
                value = emergencyPhone,
                onValueChange = { emergencyPhone = it }
            )
            UserInfoField(
                label = "Family Medical History",
                value = medicalHistory,
                onValueChange = { medicalHistory = it }
            )
            UserInfoField(
                label = "Known Health Conditions",
                value = knownConditions,
                onValueChange = { knownConditions = it }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val data = ExtraDetails(
                        emergencyContactName = emergencyName,
                        emergencyContactRelation = emergencyRelation,
                        emergencyContactPhone = emergencyPhone,
                        familyMedicalHistory = medicalHistory,
                        knownHealthConditions = knownConditions
                    )
                    settingsViewModel.addExtraDetails(data, userId)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
    if (isDialog) {
        CustomLoader()
    }
}

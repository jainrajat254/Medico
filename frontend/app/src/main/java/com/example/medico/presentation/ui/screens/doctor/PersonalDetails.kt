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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.Uri
import com.example.medico.domain.model.EditDocPersonalDetails
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.ProfileImage
import com.example.medico.presentation.ui.screens.UserInfoField
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun DocPersonalDetails(
    sharedPreferencesManager: SharedPreferencesManager,
    settingsViewModel: SettingsViewModel,
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var name by remember { mutableStateOf("") }
    var uid by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var isDialog by remember { mutableStateOf(false) }

    val docId = sharedPreferencesManager.getDocProfile()?.id ?: ""
    val editDocPersonalState by settingsViewModel.editDocPersonalState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        sharedPreferencesManager.getDocProfile()?.let { profile ->
            val doctorName = "${profile.firstName} ${profile.lastName}"
            name = doctorName
            gender = profile.gender
            dob = profile.dob
            email = profile.email
            uid = profile.uid
            phone = profile.phone
        }
    }

    LaunchedEffect(editDocPersonalState) {
        when (val result = editDocPersonalState) {
            is ResultState.Success -> {
                isDialog = false
                Toast.makeText(context, "Personal Details Updated", Toast.LENGTH_SHORT).show()

                sharedPreferencesManager.getDocProfile()?.let { currentProfile ->
                    val updatedProfile = currentProfile.copy(
                        uid = uid,
                        phone = phone,
                        email = email,
                        dob = dob
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
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    ProfileImage(
                        isEditing = false,
                        selectedImageUri = null,
                        onImageSelect = { /* no-op */ }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔒 Read-only fields
                    UserInfoField(
                        label = "Name",
                        value = name,
                        onValueChange = {},
                    )
                    UserInfoField(
                        label = "Gender",
                        value = gender,
                        onValueChange = {},
                    )
                    UserInfoField(
                        label = "DOB (YYYY/MM/DD)",
                        value = dob,
                        onValueChange = { dob = it },
                    )

                    UserInfoField(
                        label = "UID",
                        value = uid,
                        onValueChange = { uid = it },
                        keyboardType = KeyboardType.Number
                    )
                    UserInfoField(
                        label = "Phone",
                        value = phone,
                        onValueChange = { phone = it },
                        keyboardType = KeyboardType.Phone
                    )
                    UserInfoField(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        keyboardType = KeyboardType.Email
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (uid.isBlank() || phone.isBlank() || email.isBlank()) {
                                Toast.makeText(
                                    context,
                                    "Please fill all editable fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            val data = EditDocPersonalDetails(
                                uid = uid,
                                phone = phone,
                                email = email,
                                dob = dob
                            )
                            settingsViewModel.editDocPersonalDetails(
                                data = data, id = docId
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors()
                    ) {
                        Text("Save Changes")
                    }
                }
            }
        }
    }
}


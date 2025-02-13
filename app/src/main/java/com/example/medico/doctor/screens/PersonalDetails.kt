package com.example.medico.doctor.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.ProfileImage
import com.example.medico.common.utils.UserInfoField
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.doctor.dto.EditDocPersonalDetails
import com.example.medico.doctor.viewModel.DoctorDetails
import org.koin.androidx.compose.getViewModel

@Composable
fun DocAccount(
    vm: AuthViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    val doctorDetails: DoctorDetails = getViewModel()

    val context = LocalContext.current

    val isEditing by doctorDetails.isEditing.collectAsState()
    val id by doctorDetails.id.collectAsState()
    val name by doctorDetails.name.collectAsState()
    val uid by doctorDetails.uid.collectAsState()
    val gender by doctorDetails.gender.collectAsState()
    val dob by doctorDetails.dob.collectAsState()
    val phone by doctorDetails.phone.collectAsState()
    val email by doctorDetails.email.collectAsState()
    val selectedImageUri by doctorDetails.selectedImageUri.collectAsState()

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                ProfileImage(
                    isEditing = isEditing,
                    selectedImageUri = selectedImageUri,
                    onImageSelect = { uri -> doctorDetails.updateSelectedImageUri(uri) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                UserInfoField(
                    label = "Name",
                    value = name,
                    isEditing = false,
                    onValueChange = {}
                )
                UserInfoField(
                    label = "Gender",
                    value = gender,
                    isEditing = false,
                    onValueChange = {}
                )
                UserInfoField(
                    label = "UID",
                    value = uid,
                    isEditing = isEditing,
                    onValueChange = doctorDetails::updateUid,
                    keyboardType = KeyboardType.Number
                )
                UserInfoField(
                    label = "DOB (DD/MM/YYYY)",
                    value = dob,
                    isEditing = isEditing,
                    onValueChange = doctorDetails::updateDOB
                )
                UserInfoField(
                    label = "Phone",
                    value = phone,
                    isEditing = isEditing,
                    onValueChange = doctorDetails::updatePhone,
                    keyboardType = KeyboardType.Phone
                )
                UserInfoField(
                    label = "Email",
                    value = email,
                    isEditing = isEditing,
                    onValueChange = doctorDetails::updateEmail,
                    keyboardType = KeyboardType.Email
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (isEditing) {
                            val formError = doctorDetails.isPersonalDetailsFormValid()
                            if (formError == null) {
                                val data = EditDocPersonalDetails(uid, dob, phone, email)
                                vm.editDocPersonalDetails(
                                    data,
                                    id,
                                    onSuccess = {
                                        Toast.makeText(
                                            context,
                                            "Details Updated",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        sharedPreferencesManager.editDocPersonalDetails(
                                            dob,
                                            uid,
                                            phone,
                                            email
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

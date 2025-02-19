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
import androidx.compose.ui.unit.dp
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.common.utils.ProfileImage
import com.example.medico.common.utils.UserInfoField
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.dto.EditUserPersonalDetails
import com.example.medico.user.model.ExtraDetails
import com.example.medico.user.viewModel.UserDetails
import org.koin.androidx.compose.getViewModel

@Composable
fun UserPersonalDetails(vm: AuthViewModel, sharedPreferencesManager: SharedPreferencesManager) {
    val userDetails: UserDetails = getViewModel()

    val context = LocalContext.current

    val isEditing by userDetails.isEditing.collectAsState()
    val id by userDetails.id.collectAsState()
    val name by userDetails.name.collectAsState()
    val age by userDetails.age.collectAsState()
    val gender by userDetails.gender.collectAsState()
    val height by userDetails.height.collectAsState()
    val weight by userDetails.weight.collectAsState()
    val dob by userDetails.dob.collectAsState()
    val bloodGroup by userDetails.bloodGroup.collectAsState()
    val phone by userDetails.phone.collectAsState()
    val email by userDetails.email.collectAsState()
    val selectedImageUri by userDetails.selectedImageUri.collectAsState()

    var personalInfoId by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        vm.getExtraDetails(
            userId = id,
            onSuccess = { userDetailsResponse ->
                Log.d("GetExtraDetails", "Fetched details: $userDetailsResponse")

                userDetails.updateHeight(userDetailsResponse.height)
                userDetails.updateWeight(userDetailsResponse.weight)
                userDetails.updateDob(userDetailsResponse.dob)

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

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    ProfileImage(
                        isEditing = isEditing,
                        selectedImageUri = selectedImageUri,
                        onImageSelect = { uri -> userDetails.updateSelectedImageUri(uri) }
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
                        label = "Age",
                        value = age,
                        isEditing = isEditing,
                        onValueChange = userDetails::updateAge,
                        keyboardType = KeyboardType.Number
                    )
                    UserInfoField(
                        label = "Blood Group",
                        value = bloodGroup,
                        isEditing = isEditing,
                        onValueChange = userDetails::updateBloodGroup
                    )
                    UserInfoField(
                        label = "Height",
                        value = height,
                        isEditing = isEditing,
                        onValueChange = userDetails::updateHeight,
                        keyboardType = KeyboardType.Phone
                    )
                    UserInfoField(
                        label = "Weight",
                        value = weight,
                        isEditing = isEditing,
                        onValueChange = userDetails::updateWeight,
                        keyboardType = KeyboardType.Number
                    )
                    UserInfoField(
                        label = "DOB",
                        value = dob,
                        isEditing = isEditing,
                        onValueChange = userDetails::updateDob
                    )
                    UserInfoField(
                        label = "Phone",
                        value = phone,
                        isEditing = isEditing,
                        onValueChange = userDetails::updatePhone,
                        keyboardType = KeyboardType.Phone
                    )
                    UserInfoField(
                        label = "Email",
                        value = email,
                        isEditing = isEditing,
                        onValueChange = userDetails::updateEmail,
                        keyboardType = KeyboardType.Email
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (isEditing) {
                                val formError = userDetails.isFormValid()
                                if (formError == null) {
                                    val data =
                                        EditUserPersonalDetails(age, bloodGroup, phone, email)
                                    val extraData = ExtraDetails(
                                        height = height,
                                        weight = weight,
                                        dob = dob
                                    )
                                    vm.editDetails(
                                        data,
                                        id,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "Details Updated",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            sharedPreferencesManager.editUserDetails(
                                                age,
                                                bloodGroup,
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
                                    vm.addExtraDetails(
                                        extraData,
                                        personalInfoId,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "Details Updated",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val updatedUserDetails =
                                                sharedPreferencesManager.getUserDetails().copy(
                                                    height = height,
                                                    weight = weight,
                                                    dob = dob
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
                                    Toast.makeText(context, formError, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                // Start editing
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


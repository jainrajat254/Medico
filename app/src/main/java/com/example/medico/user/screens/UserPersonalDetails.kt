package com.example.medico.user.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.medico.R
import com.example.medico.user.dto.EditUserPersonalDetails
import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.user.model.UserDetails
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.common.utils.HeaderSection
import com.example.medico.common.utils.ProfileImage
import com.example.medico.common.utils.Tagline
import com.example.medico.common.utils.UserInfoField
import org.koin.androidx.compose.getViewModel

@Composable
fun UserAccount(vm: AuthViewModel, sharedPreferencesManager: SharedPreferencesManager) {
    val userDetails: UserDetails = getViewModel()

    val context = LocalContext.current

    val isEditing by userDetails.isEditing.collectAsState()
    val id by userDetails.id.collectAsState()
    val name by userDetails.name.collectAsState()
    val age by userDetails.age.collectAsState()
    val gender by userDetails.gender.collectAsState()
    val bloodGroup by userDetails.bloodGroup.collectAsState()
    val phone by userDetails.phone.collectAsState()
    val email by userDetails.email.collectAsState()
    val selectedImageUri by userDetails.selectedImageUri.collectAsState()

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
}


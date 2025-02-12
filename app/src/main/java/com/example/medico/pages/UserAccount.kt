package com.example.medico.pages

import android.content.Context
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
import com.example.medico.data.EditUserDTO
import com.example.medico.models.AuthViewModel
import com.example.medico.models.UserAccountViewModel
import com.example.medico.sharedPreferences.SharedPreferencesManager
import org.koin.androidx.compose.getViewModel

@Composable
fun UserAccount(vm: AuthViewModel, sharedPreferencesManager: SharedPreferencesManager) {
    val userAccountViewModel: UserAccountViewModel = getViewModel()

    val context = LocalContext.current

    val isEditing by userAccountViewModel.isEditing.collectAsState()
    val id by userAccountViewModel.id.collectAsState()
    val name by userAccountViewModel.name.collectAsState()
    val age by userAccountViewModel.age.collectAsState()
    val gender by userAccountViewModel.gender.collectAsState()
    val bloodGroup by userAccountViewModel.bloodGroup.collectAsState()
    val phone by userAccountViewModel.phone.collectAsState()
    val email by userAccountViewModel.email.collectAsState()
    val selectedImageUri by userAccountViewModel.selectedImageUri.collectAsState()

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
                            onImageSelect = { uri -> userAccountViewModel.updateSelectedImageUri(uri) }
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
                            onValueChange = userAccountViewModel::updateAge,
                            keyboardType = KeyboardType.Number
                        )
                        UserInfoField(
                            label = "Blood Group",
                            value = bloodGroup,
                            isEditing = isEditing,
                            onValueChange = userAccountViewModel::updateBloodGroup
                        )
                        UserInfoField(
                            label = "Phone",
                            value = phone,
                            isEditing = isEditing,
                            onValueChange = userAccountViewModel::updatePhone,
                            keyboardType = KeyboardType.Phone
                        )
                        UserInfoField(
                            label = "Email",
                            value = email,
                            isEditing = isEditing,
                            onValueChange = userAccountViewModel::updateEmail,
                            keyboardType = KeyboardType.Email
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (isEditing) {
                                    val formError = userAccountViewModel.isFormValid()
                                    if (formError == null) {
                                        val data = EditUserDTO(age, bloodGroup, phone, email)
                                        vm.editDetails(
                                            data,
                                            id,
                                            onSuccess = {
                                                Toast.makeText(
                                                    context,
                                                    "Details Updated",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                sharedPreferencesManager.editUserDetails(age,bloodGroup,phone,email)
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
                                        userAccountViewModel.toggleEditing()
                                    } else {
                                        Toast.makeText(context, formError, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                } else {
                                    // Start editing
                                    userAccountViewModel.toggleEditing()
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


@Composable
fun ProfileImage(
    isEditing: Boolean,
    selectedImageUri: Uri?,
    onImageSelect: (Uri?) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onImageSelect(uri)
        }
    )

    Box(
        modifier = Modifier
            .size(180.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = selectedImageUri ?: R.drawable.home,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        if (isEditing) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .padding(4.dp)
//                    .offset(x = (12).dp, y = (-14).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "Add",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
            }
        }
    }
}

@Composable
fun UserInfoField(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp)
        )
        TextField(
            value = value,
            onValueChange = { if (isEditing) onValueChange(it) },
            enabled = isEditing,
            readOnly = !isEditing,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            shape = shape
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}


package com.example.medico.pages

import android.content.Context
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.medico.R
import com.example.medico.data.EditDocDTO
import com.example.medico.data.EditUserDTO
import com.example.medico.models.AuthViewModel
import com.example.medico.models.DocAccountViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun DocAccount(vm: AuthViewModel) {
    val docAccountViewModel: DocAccountViewModel = getViewModel()

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    val DOC_PREFS_UID = "uid"
    val DOC_PREFS_DOB = "dob"
    val DOC_PREFS_EMAIL = "email"
    val DOC_PREFS_PHONE = "phone"


    val isEditing by docAccountViewModel.isEditing.collectAsState()
    val id by docAccountViewModel.id.collectAsState()
    val name by docAccountViewModel.name.collectAsState()
    val uid by docAccountViewModel.uid.collectAsState()
    val gender by docAccountViewModel.gender.collectAsState()
    val dob by docAccountViewModel.dob.collectAsState()
    val phone by docAccountViewModel.phone.collectAsState()
    val email by docAccountViewModel.email.collectAsState()
    val selectedImageUri by docAccountViewModel.selectedImageUri.collectAsState()

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
                            onImageSelect = { uri -> docAccountViewModel.updateSelectedImageUri(uri) }
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
                            onValueChange = docAccountViewModel::updateUid,
                            keyboardType = KeyboardType.Number
                        )
                        UserInfoField(
                            label = "DOB (DD/MM/YYYY)",
                            value = dob,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateDOB
                        )
                        UserInfoField(
                            label = "Phone",
                            value = phone,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updatePhone,
                            keyboardType = KeyboardType.Phone
                        )
                        UserInfoField(
                            label = "Email",
                            value = email,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateEmail,
                            keyboardType = KeyboardType.Email
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (isEditing) {
                                    val formError = docAccountViewModel.isPersonalDetailsFormValid()
                                    if (formError == null) {
                                        val data = EditDocDTO(uid, dob, phone, email)
                                        vm.editDocPersonalDetails(
                                            data,
                                            id,
                                            onSuccess = {
                                                Toast.makeText(
                                                    context,
                                                    "Details Updated",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                sharedPreferences.edit().apply {
                                                    putString(DOC_PREFS_DOB, dob)
                                                    putString(DOC_PREFS_UID, uid)
                                                    putString(DOC_PREFS_PHONE, phone)
                                                    putString(DOC_PREFS_EMAIL, email)
                                                    apply()
                                                }
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
                                        docAccountViewModel.toggleEditing()
                                    } else {
                                        Toast.makeText(context, formError, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                } else {
                                    docAccountViewModel.toggleEditing()
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
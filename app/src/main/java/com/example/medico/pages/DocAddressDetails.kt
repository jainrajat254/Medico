package com.example.medico.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medico.R
import com.example.medico.data.DocAddressDetailsDTO
import com.example.medico.data.EditDocDTO
import com.example.medico.models.AuthViewModel
import com.example.medico.models.DocAccountViewModel
import com.example.medico.sharedPreferences.SharedPreferencesManager
import org.koin.androidx.compose.getViewModel

@Composable
fun DocAddressDetails(
    sharedPreferencesManager: SharedPreferencesManager,
    vm: AuthViewModel,
) {

    val docAccountViewModel: DocAccountViewModel = getViewModel()
    val context = LocalContext.current

    val isEditing by docAccountViewModel.isEditing.collectAsState()
    val id by docAccountViewModel.id.collectAsState()
    val address by docAccountViewModel.address.collectAsState()
    val state by docAccountViewModel.state.collectAsState()
    val district by docAccountViewModel.district.collectAsState()
    val zipCode by docAccountViewModel.zipCode.collectAsState()
    val workspaceName by docAccountViewModel.workspaceName.collectAsState()

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

                // Lazy Column for User Info Fields
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
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateWorkspaceName
                        )
                        UserInfoField(
                            label = "Address",
                            value = address,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateAddress,
                        )
                        UserInfoField(
                            label = "State",
                            value = state,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateState
                        )
                        UserInfoField(
                            label = "District",
                            value = district,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateDistrict,
                        )
                        UserInfoField(
                            label = "Zip Code",
                            value = zipCode,
                            isEditing = isEditing,
                            onValueChange = docAccountViewModel::updateZipCode
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (isEditing) {
                                    val formError = docAccountViewModel.isPersonalDetailsFormValid()
                                    if (formError == null) {
                                        val data = DocAddressDetailsDTO(workspaceName,address,state,district,zipCode)
                                        vm.editDocAddressDetails(
                                            data,
                                            id,
                                            onSuccess = {
                                                Toast.makeText(
                                                    context,
                                                    "Details Updated",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                sharedPreferencesManager.editDocAddressDetails(workspaceName,address,state,district,zipCode)
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

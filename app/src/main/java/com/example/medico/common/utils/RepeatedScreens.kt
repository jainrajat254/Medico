package com.example.medico.common.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.medico.R
import com.example.medico.doctor.viewModel.DoctorRegister


@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "MEDICO",
            fontSize = 24.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            color = Color.White
        )

        Image(
            painter = painterResource(id = R.drawable.app),
            contentDescription = "App Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(50))
        )
    }
}

@Composable
fun Tagline() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Health, Your History",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Secure, Simple, Smart!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TaglineAndProfilePicture() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 60.dp, top = 12.dp)
            ) {
                Text(
                    text = "Your Health, Your History",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Secure, Simple, Smart!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Profile Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(50))
                    .align(Alignment.CenterEnd) // Aligns the image to the end
            )
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

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    trailingIcon: @Composable() (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        readOnly = readOnly,
        modifier = modifier,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        trailingIcon = trailingIcon,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        ),
    )
}


@Composable
fun UserPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp)
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(12.dp))
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
fun BackgroundContent(paddingValues: PaddingValues, content: @Composable () -> Unit) {
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
            content()
        }
    }
}

@Composable
fun LoginForm(
    username: String,
    password: String,
    label: String,
    passwordVisible: Boolean,
    usernameError: Boolean,
    passwordError: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordToggle: () -> Unit,
    onLogin: () -> Unit,
    onRegisterClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.displayLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.padding(bottom = 40.dp))

            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChange,
                label = { Text(text = label) },
                isError = usernameError,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                )
            )
            if (usernameError) {
                Text(
                    text = stringResource(id = R.string.username_error),
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(text = stringResource(id = R.string.password)) },
                isError = passwordError,
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = onPasswordToggle) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                )
            )
            if (passwordError) {
                Text(
                    text = stringResource(id = R.string.password_error),
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.not_having_an_account),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                TextButton(onClick = onRegisterClick) {
                    Text(
                        text = stringResource(id = R.string.register),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun StateDistrictDropdown(
    viewModel: DoctorRegister,
    onDistrictSelected: (String) -> Unit,
) {
    val selectedState = viewModel.state.collectAsState().value
    val selectedDistrict = viewModel.district.collectAsState().value

    val districts = mapOf(
        "Delhi" to listOf(
            "Central Delhi",
            "East Delhi",
            "New Delhi",
            "North Delhi",
            "North East Delhi",
            "North West Delhi",
            "Shahdara",
            "South Delhi",
            "South East Delhi",
            "South West Delhi",
            "West Delhi"
        ),
        "Haryana" to listOf(
            "Ambala",
            "Bhiwani",
            "Charkhi Dadri",
            "Faridabad",
            "Fatehabad",
            "Gurugram",
            "Hisar",
            "Jhajjar",
            "Jind",
            "Kaithal",
            "Karnal",
            "Kurukshetra",
            "Mahendragarh",
            "Nuh",
            "Palwal",
            "Panchkula",
            "Panipat",
            "Rewari",
            "Rohtak",
            "Sirsa",
            "Sonipat",
            "Yamunanagar"
        ),
        "Uttar Pradesh" to listOf(
            "Agra",
            "Aligarh",
            "Ambedkar Nagar",
            "Amethi",
            "Amroha",
            "Auraiya",
            "Ayodhya",
            "Azamgarh",
            "Baghpat",
            "Bahraich",
            "Ballia",
            "Balrampur",
            "Banda",
            "Barabanki",
            "Bareilly",
            "Basti",
            "Bhadohi",
            "Bijnor",
            "Budaun",
            "Bulandshahr",
            "Chandauli",
            "Chitrakoot",
            "Deoria",
            "Etah",
            "Etawah",
            "Farrukhabad",
            "Fatehpur",
            "Firozabad",
            "Gautam Buddha Nagar",
            "Ghaziabad",
            "Ghazipur",
            "Gonda",
            "Gorakhpur",
            "Hamirpur",
            "Hapur",
            "Hardoi",
            "Hathras",
            "Jalaun",
            "Jaunpur",
            "Jhansi",
            "Kannauj",
            "Kanpur Dehat",
            "Kanpur Nagar",
            "Kasganj",
            "Kaushambi",
            "Kheri",
            "Kushinagar",
            "Lalitpur",
            "Lucknow",
            "Maharajganj",
            "Mahoba",
            "Mainpuri",
            "Mathura",
            "Mau",
            "Meerut",
            "Mirzapur",
            "Moradabad",
            "Muzaffarnagar",
            "Pilibhit",
            "Pratapgarh",
            "Prayagraj",
            "Raebareli",
            "Rampur",
            "Saharanpur",
            "Sambhal",
            "Sant Kabir Nagar",
            "Shahjahanpur",
            "Shamli",
            "Shrawasti",
            "Siddharthnagar",
            "Sitapur",
            "Sonbhadra",
            "Sultanpur",
            "Unnao",
            "Varanasi"
        ),
        "Uttarakhand" to listOf(
            "Almora",
            "Bageshwar",
            "Chamoli",
            "Champawat",
            "Dehradun",
            "Haridwar",
            "Nainital",
            "Pauri Garhwal",
            "Pithoragarh",
            "Rudraprayag",
            "Tehri Garhwal",
            "Udham Singh Nagar",
            "Uttarkashi"
        ),
        "Goa" to listOf(
            "North Goa",
            "South Goa"
        )
    )

    Column {
        // State Dropdown
        CommonDropDownMenu(
            items = districts.keys.toList(),
            selectedItem = selectedState,
            onItemSelected = {
                viewModel.updateState(it)
                viewModel.updateDistrict("") // Reset district when state changes
            },
            label = "State"
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedState.isNotEmpty()) {
            CommonDropDownMenu(
                items = districts[selectedState] ?: emptyList(),
                selectedItem = selectedDistrict,
                onItemSelected = onDistrictSelected,
                label = "District"
            )
        }
    }
}

@Composable
fun BloodGroupDropdown(
    selectedBloodGroup: String,
    onBloodGroupSelected: (String) -> Unit,
) {
    val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    CommonDropDownMenu(
        items = bloodGroups,
        selectedItem = selectedBloodGroup,
        onItemSelected = { bloodGroup ->
            onBloodGroupSelected(bloodGroup)
        },
        label = "Blood Group"
    )
}

@Composable
fun GenderDropdown(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
) {
    val genderOptions = listOf("Male", "Female", "Other")
    CommonDropDownMenu(
        items = genderOptions,
        selectedItem = selectedGender,
        onItemSelected = { gender ->
            onGenderSelected(gender)  // Notify the parent composable to update the ViewModel
        },
        label = "Gender"
    )
}

@Composable
fun CommonDropDownMenu(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    label: String,
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isTextFieldEditable by remember { mutableStateOf(false) } // Track if the TextField is editable

    // Filter items based on searchText
    val filteredItems = if (searchText.isEmpty()) {
        items
    } else {
        items.filter { it.contains(searchText, ignoreCase = true) }
    }

    Box {
        CustomTextField(
            value = selectedItem,
            onValueChange = {}, // Don't allow typing, only selecting
            label = label,
            readOnly = !isTextFieldEditable, // Make it editable only when the dropdown is open
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true; isTextFieldEditable = true }, // Open dropdown and enable typing
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    tint = Color.Black,
                    modifier = Modifier.clickable { expanded = true; isTextFieldEditable = true }
                )
            }
        )

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                isTextFieldEditable = false // Disable editing after dropdown closes
            }
        ) {
            // Add a TextField for search input
            DropdownMenuItem(
                text = {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text(text = "Search...", color = Color.White) },
                        textStyle = TextStyle(color = Color.White),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                },
                onClick = {} // No action when clicking on the search field
            )

            // Display filtered items
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            style = TextStyle(color = Color.White)
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                        searchText = "" // Clear search text after selection
                        expanded = false
                        isTextFieldEditable = false // Disable editing once an item is selected
                    }
                )
            }
        }
    }
}



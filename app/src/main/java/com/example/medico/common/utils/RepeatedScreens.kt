package com.example.medico.common.utils

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.medico.R
import com.example.medico.common.model.Districts
import com.example.medico.doctor.viewModel.DoctorRegister
import com.example.medico.user.dto.MedicationsDTO
import com.example.medico.user.responses.MedicationResponse
import com.example.medico.user.responses.ReportsResponse
import java.util.Calendar

@Composable
fun AttentionLevelDropdown(
    selectedType: String,
    onTypeSelected: (String) -> Unit,
    modifier: Modifier,
) {
    val typeOptions = listOf(
        "Normal", "Attention", "Urgent"
    )

    CommonDropDownMenu(
        items = typeOptions,
        selectedItem = selectedType,
        onItemSelected = onTypeSelected,
        label = "Medication Type",
        modifier = modifier,
        allowCustomInput = true
    )
}


@Composable
fun MedicationTypeDropdown(
    selectedType: String,
    onTypeSelected: (String) -> Unit,
    modifier: Modifier,
) {
    val typeOptions = listOf(
        "Tablet", "Capsule", "Syrup",
        "Injection", "Ointment",
        "Drops", "Inhaler", "Powder"
    )

    CommonDropDownMenu(
        items = typeOptions,
        selectedItem = selectedType,
        onItemSelected = onTypeSelected,
        label = "Medication Type",
        modifier = modifier,
        allowCustomInput = true
    )
}

@Composable
fun DosageDropdown(
    selectedType: String,
    selectedDosage: String,
    onDosageSelected: (String) -> Unit,
    modifier: Modifier,
) {
    val dosageOptions = when (selectedType) {
        "Tablet", "Capsule" -> listOf("1 tablet", "2 tablets", "Half tablet", "1 capsule")
        "Syrup" -> listOf("5 ml", "10 ml", "15 ml")
        "Drops" -> listOf("1 drop", "2 drops", "3 drops")
        "Injection" -> listOf("0.5 ml", "1 ml", "2 ml")
        "Ointment" -> listOf("Apply thin layer", "Apply thick layer")
        "Inhaler" -> listOf("1 puff", "2 puffs")
        "Powder" -> listOf("1 teaspoon", "Half teaspoon")
        else -> listOf("Not applicable")
    }

    CommonDropDownMenu(
        items = dosageOptions,
        selectedItem = selectedDosage,
        onItemSelected = onDosageSelected,
        label = "Dosage",
        modifier = modifier,
        allowCustomInput = true
    )
}

@Composable
fun FrequencyDropdown(
    selectedFrequency: String,
    onFrequencySelected: (String) -> Unit,
    modifier: Modifier,
) {
    val frequencyOptions = listOf(
        "Once a day", "Twice a day", "Thrice a day", "Four times a day",
        "Morning", "Afternoon", "Night", "Before bed",
        "Empty stomach", "Before meals", "After meals", "With food",
        "Alternate days", "Weekly", "As needed"
    )

    CommonDropDownMenu(
        items = frequencyOptions,
        selectedItem = selectedFrequency,
        onItemSelected = onFrequencySelected,
        label = "Frequency",
        modifier = modifier,
        allowCustomInput = true
    )
}

@Composable
fun DurationDropdown(
    selectedDuration: String,
    onDurationSelected: (String) -> Unit,
    modifier: Modifier,
) {
    val durationOptions = listOf(
        "3 days", "5 days", "7 days", "10 days",
        "2 weeks", "1 month", "Ongoing"
    )

    CommonDropDownMenu(
        items = durationOptions,
        selectedItem = selectedDuration,
        onItemSelected = onDurationSelected,
        label = "Duration",
        modifier = modifier,
        allowCustomInput = true
    )
}

@Composable
fun IntakeMethodDropdown(
    selectedMethod: String,
    onMethodSelected: (String) -> Unit,
    modifier: Modifier,
) {
    val methodOptions = listOf(
        "With Water", "With Milk", "With Food",
        "Before Food", "After Food",
        "Sublingual", "Inhalation"
    )

    CommonDropDownMenu(
        items = methodOptions,
        selectedItem = selectedMethod,
        onItemSelected = onMethodSelected,
        label = "Intake Method",
        modifier = modifier,
        allowCustomInput = true
    )
}


@Composable
fun CommonDropDownMenu(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    allowCustomInput: Boolean = false, // Added parameter to allow custom input
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isTextFieldEditable by remember { mutableStateOf(false) }
    var customText by remember { mutableStateOf("") }
    var isCustomSelected by remember { mutableStateOf(false) }

    // Add "Custom" option if allowed
    val finalItems = if (allowCustomInput) items + "Custom" else items

    // Filter items based on searchText
    val filteredItems = if (searchText.isEmpty()) {
        finalItems
    } else {
        finalItems.filter { it.contains(searchText, ignoreCase = true) }
    }

    Box {
        CustomTextField(
            value = if (isCustomSelected) customText else selectedItem,
            onValueChange = {
                if (isCustomSelected) {
                    customText = it
                    onItemSelected(it)
                }
            },
            label = label,
            readOnly = !(isTextFieldEditable || isCustomSelected),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = true; isTextFieldEditable = true
                },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    tint = Color.Black,
                    modifier = Modifier.clickable {
                        expanded = true; isTextFieldEditable = true
                    }
                )
            }
        )

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                isTextFieldEditable = false
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
                        if (item == "Custom") {
                            isCustomSelected = true
                            customText = "" // Clear previous input
                        } else {
                            isCustomSelected = false
                            onItemSelected(item)
                        }
                        searchText = ""
                        expanded = false
                        isTextFieldEditable = false
                    }
                )
            }
        }
    }
}

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
            .padding(horizontal = 16.dp, vertical = 0.dp),
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
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    enabled: Boolean = true
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
        enabled = enabled
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
fun BackgroundContent(
    paddingValues: PaddingValues,
    showTagline: Boolean = true,
    content: @Composable () -> Unit,
) {
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
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderSection()
            if (showTagline) {
                Spacer(modifier = Modifier.height(16.dp))
                Tagline()
            }
            Spacer(modifier = Modifier.height(32.dp))
            content()
        }
    }
}

@Composable
fun BackgroundContentHome(
    paddingValues: PaddingValues,
    name: String,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background_app),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Header and Profile Picture Section
        HeaderSection()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(start = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome, $name",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 64.dp)
            )

            TaglineAndProfilePicture()
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
    onRegisterClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
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

    val districts = Districts.districts


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
fun SlotDropdown(
    selectedSlot: String,
    availableSlots: List<String>,
    onSlotSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    CommonDropDownMenu(
        items = availableSlots,
        selectedItem = selectedSlot,
        onItemSelected = { slot ->
            onSlotSelected(slot)
        },
        label = "Time Slot",
        modifier = modifier
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
            onGenderSelected(gender)
        },
        label = "Gender"
    )
}

@Composable
fun MedicationCardUser(
    medication: MedicationResponse,
    showActions: Boolean = false,
    onUpdateClick: (() -> Unit)? = null,
    onRemoveClick: (() -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Placeholder for medication image
            Image(
                painter = painterResource(id = R.drawable.capsule),
                contentDescription = "Medication Icon",
                modifier = Modifier.size(48.dp)
            )

            // Medication Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = medication.medicationName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4771CC)
                )

                Text(
                    text = "Dosage: ${medication.dosageType}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Text(
                    text = "Type: ${medication.medicationType}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )

                Text(
                    text = "Intake Method: ${medication.intakeMethod}",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = "Frequency: ${medication.frequency}",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = "Duration: ${medication.duration}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }


            if (showActions) {
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert, // Three-dot menu
                            contentDescription = "More Options",
                            tint = Color.Black
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Update") },
                            onClick = {
                                expanded = false
                                onUpdateClick?.invoke()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Remove") },
                            onClick = {
                                expanded = false
                                onRemoveClick?.invoke()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedicationCardDoc(
    medication: MedicationsDTO,
    showActions: Boolean = false,
    onUpdateClick: (() -> Unit)? = null,
    onRemoveClick: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Placeholder for medication image
            Image(
                painter = painterResource(id = R.drawable.capsule),
                contentDescription = "Medication Icon",
                modifier = Modifier.size(48.dp)
            )

            // Medication Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = medication.medicationName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4771CC)
                )

                Text(
                    text = "Dosage: ${medication.dosageType}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Text(
                    text = "Type: ${medication.medicationType}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )

                Text(
                    text = "Intake Method: ${medication.intakeMethod}",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = "Frequency: ${medication.frequency}",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = "Duration: ${medication.duration}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }


            if (showActions) {
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert, // Three-dot menu
                            contentDescription = "More Options",
                            tint = Color.Black
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Update") },
                            onClick = {
                                expanded = false
                                onUpdateClick?.invoke()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Remove") },
                            onClick = {
                                expanded = false
                                onRemoveClick?.invoke()
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ReportCard(
    report: ReportsResponse,
    showExportButton: Boolean = false,  // Control visibility of Export button
    onExportClick: (() -> Unit)? = null,
    onViewFullReportClick: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF4771CC),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Report Name
            Text(
                text = report.reportName,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D4159)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Reviewed by: Dr. ${report.reviewedBy}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Attention Text with conditional color
            Text(
                text = report.attentionLevel,
                style = TextStyle(
                    color = if (report.attentionLevel.contains("Attention", ignoreCase = true))
                        Color.Red
                    else
                        Color.Green,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Row for buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showExportButton) {
                    Button(
                        onClick = { onExportClick?.invoke() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Export", color = Color.White, fontSize = 14.sp)
                    }
                }

                Button(
                    onClick = { onViewFullReportClick?.invoke() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("View Full Report", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun CurrentPatientCard(
    patientName: String,
    index: Int,
    showPersonalInfoOnly: Boolean = false,  // Toggle button visibility
    onRecordsClick: (() -> Unit)? = null,
    onDoneClick: (() -> Unit)? = null,
    onAbsentClick: (() -> Unit)? = null,
    onPersonalInfoClick: (() -> Unit)? = null

    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFF4771CC), RoundedCornerShape(16.dp))
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Current Patient",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = patientName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "#$index",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showPersonalInfoOnly) {
                    // Show only the "Personal Info" button
                    Button(
                        onClick = { onPersonalInfoClick?.invoke() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Personal Info", color = Color.White, fontSize = 12.sp)
                    }
                } else {
                    Button(
                        onClick = { onRecordsClick?.invoke() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Records", color = Color.White, fontSize = 12.sp)
                    }

                    Button(
                        onClick = { onDoneClick?.invoke() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Done", color = Color.White, fontSize = 12.sp)
                    }

                    Button(
                        onClick = { onAbsentClick?.invoke() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Absent", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 2.dp) // Spacing between label and value
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 2, // Allows longer values to show
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val minDate = calendar.timeInMillis

    val defaultYear = calendar.get(Calendar.YEAR)
    val defaultMonth = calendar.get(Calendar.MONTH)
    val defaultDay = calendar.get(Calendar.DAY_OF_MONTH)

    // Set maximum date to one week from today
    calendar.add(Calendar.DAY_OF_MONTH, 6) // 1 already added, so add 6 more
    val maxDate = calendar.timeInMillis

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            onDateSelected("$dayOfMonth/${month + 1}/$year")  // Formatting date
        },
        defaultYear,
        defaultMonth,
        defaultDay
    )

    // Set date range
    datePickerDialog.datePicker.minDate = minDate
    datePickerDialog.datePicker.maxDate = maxDate

    datePickerDialog.show()
}

@Composable
fun NotAvailable(label: String) {
    Card(
        modifier = Modifier
            .height(124.dp)
            .padding(top = 30.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF4771CC),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = label,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4771CC),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
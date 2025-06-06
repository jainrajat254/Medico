package com.example.medico.presentation.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import coil3.compose.AsyncImage
import com.example.medico.R
import com.example.medico.domain.model.Districts
import com.example.medico.domain.model.MedicationResponse
import com.example.medico.domain.model.MedicationsDTO
import com.example.medico.domain.model.OldMedicationsDTO
import com.example.medico.domain.model.ReportsResponse
import com.example.medico.presentation.viewmodel.AuthViewModel
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
            style = MaterialTheme.typography.titleLarge.copy( // Ensures scalability
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.onPrimary // Supports dark mode
            ),
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.app),
            contentDescription = "App Logo",
            contentScale = ContentScale.Fit, // Prevents unwanted cropping
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape) // Better for circular images
        )
    }
}


@Composable
fun Tagline() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Health, Your History",
            style = MaterialTheme.typography.titleLarge.copy( // Better typography management
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Secure, Simple, Smart!",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimary
            ),
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Ensures text and image are properly aligned
    ) {
        Column(
            horizontalAlignment = Alignment.Start // Left-align text
        ) {
            Text(
                text = "Your Health, Your History",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary, // Better theming support
                textAlign = TextAlign.Start
            )
            Text(
                text = "Secure, Simple, Smart!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start
            )
        }

        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Profile Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(50))
        )
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
fun UserInfoField(
    label: String,
    value: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    modifier: Modifier = Modifier,
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
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = false,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
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
    enabled: Boolean = true,
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
    val isDarkMode: Boolean = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Image(
            painter = painterResource(
                id = if (isDarkMode) R.drawable.dark else R.drawable.background_app
            ),
            contentDescription = "App Background", // Accessibility improvement
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            HeaderSection()

            if (showTagline) {
                Spacer(modifier = Modifier.height(16.dp))
                Tagline()
                Spacer(modifier = Modifier.height(32.dp)) // Moved inside to avoid unnecessary space
            }

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
    val isDarkMode: Boolean = isSystemInDarkTheme()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        // Background Image
        Image(
            painter = painterResource(
                id = if (isDarkMode) R.drawable.dark else R.drawable.background_app
            ), contentDescription = null,
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            style = MaterialTheme.typography.displayLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        )

        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text(text = label) },
            isError = usernameError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        if (usernameError) {
            Text(
                text = stringResource(id = R.string.username_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

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
                        contentDescription = ("Password")
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

        )
        if (passwordError) {
            Text(
                text = stringResource(id = R.string.password_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Button(
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp), // ✅ Softer button corners
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp) // ✅ Modern elevation
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

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


@Composable
fun StateDistrictDropdown(
    viewModel: AuthViewModel,
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
fun OldMedicationCard(
    medication: OldMedicationsDTO,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Medication Icon
            Image(
                painter = painterResource(id = R.drawable.capsule),
                contentDescription = "Medication Icon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
            )

            // Medication Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = medication.medicationName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4771CC)
                )

                Text(
                    text = "Prescribed by: ${medication.doctorName}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Text(
                    text = "Start: ${medication.startDate}  |  End: ${medication.endDate}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
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
            .wrapContentHeight()
            .padding(vertical = 4.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Medication Icon
            Image(
                painter = painterResource(id = R.drawable.capsule),
                contentDescription = "Medication Icon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
            )

            // Medication Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = medication.medicationName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4771CC)
                )

                Text(
                    text = "${medication.dosageType} • ${medication.medicationType}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Text(
                    text = "${medication.intakeMethod} • ${medication.frequency} • ${medication.duration}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )

                Text(
                    text = "Start Date: ${medication.startDate}",
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            if (showActions) {
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
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
    onRemoveClick: (() -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Medication Icon
            Image(
                painter = painterResource(id = R.drawable.capsule),
                contentDescription = "Medication Icon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
            )

            // Medication Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = medication.medicationName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4771CC)
                )

                Text(
                    text = "${medication.dosageType} • ${medication.medicationType}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Text(
                    text = "${medication.intakeMethod} • ${medication.frequency} • ${medication.duration}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }

            if (showActions) {
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
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
    showExportButton: Boolean = false,
    onExportClick: (() -> Unit)? = null,
    onViewFullReportClick: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp, vertical = 6.dp)  // Less padding for compactness
            .border(1.5.dp, Color(0xFF4771CC), RoundedCornerShape(12.dp))
            .shadow(6.dp, RoundedCornerShape(12.dp)),  // Softer shadow for depth
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)  // Adjusted padding for a tighter layout
        ) {
            // 🔹 Report Name (Smaller font size)
            Text(
                text = report.reportName,
                style = TextStyle(
                    fontSize = 18.sp,  // Reduced size from 24.sp
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2D4159)
                )
            )

            // 🔹 Subtle Divider with better spacing
            HorizontalDivider(
                color = Color(0xFFDDDDDD),
                thickness = 0.8.dp,  // Thinner line for a subtle effect
                modifier = Modifier.padding(vertical = 6.dp)  // Less vertical space
            )

            // 🔹 Reviewed By (Smaller but clear)
            Text(
                text = "Reviewed by: Dr. ${report.reviewedBy}",
                style = TextStyle(
                    fontSize = 14.sp,  // Reduced from 17.sp
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF555555)
                )
            )

            // 🔹 Date (Smaller and subtle)
            Text(
                text = "Date: ${report.date}",
                style = TextStyle(
                    color = Color(0xFF777777),
                    fontSize = 12.sp  // Smaller for less emphasis
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            // 🔹 Attention Level (Compact and well-styled)
            Box(
                modifier = Modifier
                    .background(
                        color = if (report.attentionLevel.contains("Attention", ignoreCase = true))
                            Color(0xFFFFEBEE)  // Light red for warning
                        else
                            Color(0xFFE8F5E9),  // Light green for normal
                        shape = RoundedCornerShape(6.dp)  // Smaller corners
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .animateContentSize()  // 🔹 Smooth transition when data updates
            ) {
                Text(
                    text = report.attentionLevel,
                    style = TextStyle(
                        color = if (report.attentionLevel.contains("Attention", ignoreCase = true))
                            Color(0xFFD32F2F)  // Darker red for emphasis
                        else
                            Color(0xFF388E3C),  // Darker green for normal
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp  // 🔺 Reduced for a balanced look
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Buttons Row (More compact & optimized)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showExportButton) {
                    Button(
                        onClick = { onExportClick?.invoke() },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)  // Reduced height for compactness
                            .shadow(4.dp, RoundedCornerShape(8.dp)),  // Softer shadow
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Export",
                                tint = Color.White
                            )
                            Text(
                                "Export",
                                color = Color.White,
                                fontSize = 14.sp
                            )  // 🔺 Reduced size
                        }
                    }
                }

                Button(
                    onClick = { onViewFullReportClick?.invoke() },
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)  // Consistent compact height
                        .shadow(4.dp, RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CADF6)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "View Report",
                            tint = Color.White
                        )
                        Text("View", color = Color.White, fontSize = 14.sp)  // 🔺 Smaller text
                    }
                }
            }
        }
    }
}


@Composable
fun CurrentPatientCard(
    patientName: String,
    index: Int,
    showAbsent: Boolean = true,
    showPersonalInfoOnly: Boolean = false,  // Toggle button visibility
    onRecordsClick: (() -> Unit)? = null,
    onDoneClick: (() -> Unit)? = null,
    onAbsentClick: (() -> Unit)? = null,
    onPersonalInfoClick: (() -> Unit)? = null,

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
                    if (showAbsent) {

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
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Reduced padding for a tighter look
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End // Aligns text to the right
        )
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
            .fillMaxWidth()
            .height(120.dp)
            .padding(vertical = 16.dp, horizontal = 24.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF4771CC),
                shape = RoundedCornerShape(16.dp)
            )
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = false),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4771CC),
                textAlign = TextAlign.Center
            )
        }
    }
}

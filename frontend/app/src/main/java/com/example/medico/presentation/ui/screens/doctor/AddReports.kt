package com.example.medico.presentation.ui.screens.doctor

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.Reports
import com.example.medico.presentation.ui.screens.AttentionLevelDropdown
import com.example.medico.presentation.ui.screens.BackgroundContent
import com.example.medico.presentation.ui.screens.common.CustomLoader
import com.example.medico.utils.ResultState
import com.example.medico.utils.SharedPreferencesManager

@Composable
fun AddReportScreen(
    navController: NavController,
    userDetails: AppointmentDTO,
    reportsViewModel: com.example.medico.presentation.viewmodel.ReportsViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
) {

    val userId = userDetails.userId
    val doctorFirstName = sharedPreferencesManager.getDocProfile()?.firstName ?: ""
    val doctorLastName = sharedPreferencesManager.getDocProfile()?.lastName ?: ""
    val doctorName = "$doctorFirstName $doctorLastName"

    val context = LocalContext.current
    var pdfUri by remember { mutableStateOf<Uri?>(null) }
    var reportName by remember { mutableStateOf("") }
    var attentionLevel by remember { mutableStateOf("Normal") }

    val pdfPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            pdfUri = uri
        }

    var isDialog by remember { mutableStateOf(false) }

    val addReportState by reportsViewModel.addReportState.collectAsState()

    LaunchedEffect(addReportState) {
        when (addReportState) {
            is ResultState.Success -> {
                isDialog = false
                Toast.makeText(context, "Report uploaded successfully", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }

            is ResultState.Error -> {
                isDialog = false
                val error = (addReportState as ResultState.Error).error.message ?: "Upload failed"
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }

            is ResultState.Loading -> isDialog = true
            is ResultState.Idle -> isDialog = false
        }
    }

    Scaffold { paddingValues ->
        BackgroundContent(paddingValues = paddingValues) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Upload Report", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = doctorName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Doctor Name") },
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = reportName,
                    onValueChange = { reportName = it },
                    label = { Text("Report Name") },
                    textStyle = TextStyle(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                AttentionLevelDropdown(
                    selectedType = attentionLevel,
                    onTypeSelected = { attentionLevel = it },
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = pdfUri?.let { getFileName(context, it) } ?: "",
                    onValueChange = {},
                    label = { Text("Select PDF") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { pdfPickerLauncher.launch("application/pdf") }) {
                            Icon(Icons.Filled.AttachFile, contentDescription = "Attach PDF")
                        }
                    },
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (isDialog) {
                    CustomLoader()
                }

                Button(
                    onClick = {
                        val uri = pdfUri
                        if (uri == null) {
                            Toast.makeText(context, "Please select a PDF", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        val pdfByteArray = getPdfByteArray(context, uri)
                        if (pdfByteArray == null) {
                            Toast.makeText(
                                context,
                                "File too large (Max: 3MB) or invalid PDF",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        if (reportName.isBlank() || doctorName.isBlank() || attentionLevel.isBlank()) {
                            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        val report = Reports(
                            reportName = reportName,
                            attentionLevel = attentionLevel,
                            reviewedBy = doctorName,
                            reportFile = pdfByteArray
                        )

                        reportsViewModel.addReport(userId = userId, report = report)
                    },
                    enabled = pdfUri != null,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4771CC))
                ) {
                    Text("Upload Report", color = Color.Black)
                }
            }
        }
    }
}


fun getPdfByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        // Get file size
        val fileSize = context.contentResolver.openFileDescriptor(uri, "r")?.statSize ?: 0
        val maxSize = 3 * 1024 * 1024 // 3MB in bytes

        if (fileSize > maxSize) {
            Log.e("PDF Conversion", "Error: File size exceeds 3MB limit. Size: $fileSize bytes")
            return null
        }

        val byteArray = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
        if (byteArray == null || byteArray.isEmpty()) {
            Log.e("PDF Conversion", "Error: PDF file is empty or failed to convert")
            return null
        }
        byteArray
    } catch (e: Exception) {
        Log.e("PDF Conversion", "Error converting PDF to ByteArray: ${e.message}")
        null
    }
}

fun getFileName(context: Context, uri: Uri): String {
    var fileName = "Unknown"
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (nameIndex != -1 && cursor.moveToFirst()) {
            fileName = cursor.getString(nameIndex)
        }
    }
    return fileName
}


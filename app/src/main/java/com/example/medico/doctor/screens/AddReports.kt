package com.example.medico.doctor.screens

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
import com.example.medico.common.utils.AttentionLevelDropdown
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.user.dto.AppointmentDTO
import com.example.medico.user.model.Reports
import com.example.medico.user.viewModel.ReportsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AddReportScreen(navController: NavController, userDetails: AppointmentDTO) {
    val context = LocalContext.current
    var pdfUri by remember { mutableStateOf<Uri?>(null) }
    var reportName by remember { mutableStateOf("") }
    var attentionLevel by remember { mutableStateOf("Normal") }

    val viewModel: ReportsViewModel = getViewModel()
    val reportsState by viewModel.reportsState.collectAsState()

    val pdfPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            pdfUri = uri
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

                // 🔹 Doctor Name (Fetched from Backend)
                OutlinedTextField(
                    value = reportsState.reviewedBy,
                    onValueChange = {}, // Doctor name is non-editable
                    readOnly = true,
                    label = { Text("Doctor Name") },
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 🔹 Report Name
                OutlinedTextField(
                    value = reportName,
                    onValueChange = { reportName = it },
                    label = { Text("Report Name") },
                    textStyle = TextStyle(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 🔹 Attention Level Dropdown
                AttentionLevelDropdown(
                    selectedType = attentionLevel,
                    onTypeSelected = { attentionLevel = it },
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 🔹 PDF Picker
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

                Button(
                    onClick = {
                        val uri = pdfUri
                        if (uri == null) {
                            Toast.makeText(context, "Please select a PDF", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val pdfByteArray = getPdfByteArray(context, uri)
                        if (pdfByteArray == null) {
                            Toast.makeText(context, "File too large (Max: 3MB) or invalid PDF", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        // ✅ Create a new Reports object
                        val report = Reports(
                            reportName = reportName,
                            attentionLevel = attentionLevel,
                            reviewedBy = reportsState.reviewedBy,
                            reportFile = pdfByteArray,
                        )

                        // ✅ Ensure all fields are set before making API call
                        if (report.reportName.isBlank() || report.reviewedBy.isBlank() || report.attentionLevel.isBlank() || report.reportFile.isEmpty()) {
                            Toast.makeText(context, "Missing required fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        // 🔥 Upload Report
                        viewModel.addReports(
                            id = userDetails.userId,
                            data = report // ✅ Pass the proper data
                        )

                        // 🔹 Navigate Back After Success
                        Toast.makeText(context, "Report Uploaded Successfully", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    enabled = pdfUri != null,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4771CC))
                )
                {
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


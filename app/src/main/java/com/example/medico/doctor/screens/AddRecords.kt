package com.example.medico.doctor.screens

import android.net.Uri
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
import com.example.medico.common.utils.BackgroundContent
import com.example.medico.user.dto.AppointmentDTO
import com.example.medico.user.model.Records
import com.example.medico.user.viewModel.RecordsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AddRecordScreen(navController: NavController, userDetails: AppointmentDTO) {
    val context = LocalContext.current
    var pdfUri by remember { mutableStateOf<Uri?>(null) }
    var recordName by remember { mutableStateOf("") }
    var review by remember { mutableStateOf("") }

    val viewModel: RecordsViewModel = getViewModel()
    val recordsState by viewModel.recordsState.collectAsState()

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
                Text(text = "Upload Record", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = recordsState.reviewedBy,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Doctor Name") },
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = recordName,
                    onValueChange = { recordName = it },
                    label = { Text("Record Name") },
                    textStyle = TextStyle(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = review,
                    onValueChange = { review = it },
                    label = { Text("Review") },
                    textStyle = TextStyle(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
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

                // Upload Button
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

                        val record = Records(
                            recordName = recordName,
                            review = review,
                            reviewedBy = recordsState.reviewedBy,
                            recordFile = pdfByteArray
                        )

                        viewModel.addRecords(userDetails.userId, record)

                        Toast.makeText(context, "Records Uploaded Successfully", Toast.LENGTH_SHORT)
                            .show()
                        navController.popBackStack()
                    },
                    enabled = pdfUri != null,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4771CC))
                ) {
                    Text("Upload Record", color = Color.Black)
                }
            }
        }
    }
}

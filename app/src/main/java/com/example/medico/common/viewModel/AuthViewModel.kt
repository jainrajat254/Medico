package com.example.medico.common.viewModel

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.medico.common.dto.EditPassword
import com.example.medico.common.koin.ApiService
import com.example.medico.common.model.LoginCredentials
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.doctor.dto.EditDocAddressDetails
import com.example.medico.doctor.dto.EditDocMedicalDetails
import com.example.medico.doctor.dto.EditDocPersonalDetails
import com.example.medico.doctor.model.DoctorDetails
import com.example.medico.doctor.responses.DoctorLoginResponse
import com.example.medico.user.dto.AppointmentDTO
import com.example.medico.user.dto.AppointmentStatus
import com.example.medico.user.dto.EditUserPersonalDetails
import com.example.medico.user.dto.MedicationsDTO
import com.example.medico.user.dto.OldMedicationsDTO
import com.example.medico.user.model.Appointments
import com.example.medico.user.model.ExtraDetails
import com.example.medico.user.model.UserDetails
import com.example.medico.user.responses.AppointmentsResponse
import com.example.medico.user.responses.MedicationResponse
import com.example.medico.user.responses.RecordsResponse
import com.example.medico.user.responses.ReportsResponse
import com.example.medico.user.responses.UserDetailsResponse
import com.example.medico.user.responses.UserLoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AuthViewModel(
    private val apiService: ApiService,
    sharedPreferencesManager: SharedPreferencesManager,
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<AppointmentsResponse>>(emptyList())
    val appointments: StateFlow<List<AppointmentsResponse>> = _appointments

    private val _medications = MutableStateFlow<List<MedicationResponse>>(emptyList())
    val medications: StateFlow<List<MedicationResponse>> = _medications

    private val _updateMedication =
        MutableStateFlow(MedicationResponse("", "", "", "", "", "", "", "", "", "", ""))
    val updateMedication: StateFlow<MedicationResponse> = _updateMedication

    private val _docMedications = MutableStateFlow<List<MedicationsDTO>>(emptyList())
    val docMedications: StateFlow<List<MedicationsDTO>> = _docMedications

    private val _oldMedications = MutableStateFlow<List<OldMedicationsDTO>>(emptyList())
    val oldMedications: StateFlow<List<OldMedicationsDTO>> = _oldMedications

    private val _reports = MutableStateFlow<List<ReportsResponse>>(emptyList())
    val reports: StateFlow<List<ReportsResponse>> = _reports

    private val _records = MutableStateFlow<List<RecordsResponse>>(emptyList())
    val records: StateFlow<List<RecordsResponse>> = _records

    private val _isRemovingMedication = MutableStateFlow(false)
    val isRemovingMedication: StateFlow<Boolean> = _isRemovingMedication.asStateFlow()

    private val _completedAppointments = MutableStateFlow<List<AppointmentDTO>>(emptyList())
    val completedAppointments: StateFlow<List<AppointmentDTO>> = _completedAppointments

    private val _todaysAppointments = MutableStateFlow<List<AppointmentDTO>>(emptyList())
    val todaysAppointments: StateFlow<List<AppointmentDTO>> = _todaysAppointments

    private val _absentAppointments = MutableStateFlow<List<AppointmentDTO>>(emptyList())
    val absentAppointments: StateFlow<List<AppointmentDTO>> = _absentAppointments

    private val _pastAppointments = MutableStateFlow<List<AppointmentDTO>>(emptyList())
    val pastAppointments: StateFlow<List<AppointmentDTO>> = _pastAppointments

    private val _futureAppointments = MutableStateFlow<List<AppointmentDTO>>(emptyList())
    val futureAppointments: StateFlow<List<AppointmentDTO>> = _futureAppointments

    fun registerUser(
        user: UserDetails,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                apiService.registerUser(user)
                onSuccess()
            } catch (e: HttpException) {
                Log.d("ViewModel", e.toString())
                onError("HTTP Error during registration: ${e.message}")
            } catch (e: Exception) {
                Log.d("ViewModel", e.toString())
                onError("Unexpected error during registration: ${e.message}")
            }
        }
    }

    fun loginDoc(
        user: LoginCredentials,
        onSuccess: (DoctorLoginResponse) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val userResponse = apiService.loginDoc(user)
                onSuccess(userResponse)
            } catch (e: HttpException) {
                Log.d("ViewModel", e.toString())
                onError("HTTP Error during login: ${e.message}")
            } catch (e: Exception) {
                Log.d("ViewModel", e.toString())
                onError("Unexpected error during login: ${e.message}")
            }
        }
    }

    fun registerDoc(
        doctorDetails: DoctorDetails,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                apiService.registerUser(doctorDetails)
                onSuccess()
            } catch (e: HttpException) {
                Log.d("ViewModel", e.toString())
                onError("HTTP Error during registration: ${e.message}")
            } catch (e: Exception) {
                Log.d("ViewModel", e.toString())
                onError("Unexpected error during registration: ${e.message}")
            }
        }
    }

    fun editDetails(
        data: EditUserPersonalDetails,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<UserDetails> = apiService.editDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun editDocPersonalDetails(
        data: EditDocPersonalDetails,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<DoctorLoginResponse> =
                    apiService.editDocPersonalDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun editPassword(
        data: EditPassword,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<UserDetails> = apiService.editPassword(data, userId)
                response.onSuccess {
                    onSuccess()
                }.onFailure {
                    onError("Error during edit: ${it.message}")
                }
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun editDocAddressDetails(
        data: EditDocAddressDetails,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<DoctorLoginResponse> =
                    apiService.editDocAddressDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun editDocMedicalDetails(
        data: EditDocMedicalDetails,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response: Result<DoctorLoginResponse> =
                    apiService.editDocMedicalDetails(data, userId)
                onSuccess()
                Log.d("data", "$data  $userId")
            } catch (e: Exception) {
                onError("Error during edit: ${e.message}")
            }
        }
    }

    fun addExtraDetails(
        data: ExtraDetails,
        userId: String,
        onSuccess: (UserDetailsResponse) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result: Result<UserDetailsResponse> = apiService.addExtraDetails(data, userId)

                result.onSuccess { response ->
                    Log.d("AddExtraDetails", "Success: $response")
                    onSuccess(response)
                }.onFailure { exception ->
                    Log.e("AddExtraDetails", "Error: ${exception.message}")
                    onError("Error during edit: ${exception.message}")
                }

            } catch (e: Exception) {
                Log.e("AddExtraDetails", "Unexpected error: ${e.message}")
                onError("Unexpected error: ${e.message}")
            }
        }
    }

    fun getPersonalInfoId(id: String, onResult: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            apiService.getPersonalInfoId(id)
                .onSuccess { uuid -> onResult(uuid) }
                .onFailure { exception -> onError(exception.message ?: "Unknown error") }
        }
    }

    fun login(
        user: LoginCredentials,
        onSuccess: (UserLoginResponse) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result: Result<UserLoginResponse> = apiService.login(user)
                result.onSuccess { response ->
                    Log.d("Login", "Success: $response")
                    onSuccess(response)
                }.onFailure { exception ->
                    Log.e("AddExtraDetails", "Error: ${exception.message}")
                    onError("Error during edit: ${exception.message}")
                }
            } catch (e: Exception) {
                Log.d("ViewModel", e.toString())
                onError("Unexpected error during login: ${e.message}")
            }
        }
    }

    fun getExtraDetails(
        userId: String,
        onSuccess: (UserDetailsResponse) -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result: Result<UserDetailsResponse> = apiService.getExtraDetails(userId)

                result.onSuccess { response ->
                    Log.d("GetExtraDetails", "Success: $response")
                    onSuccess(response)
                }.onFailure { exception ->
                    Log.e("GetExtraDetails", "Error: ${exception.message}")
                    onError("Error during edit: ${exception.message}")
                }

            } catch (e: Exception) {
                Log.e("AddExtraDetails", "Unexpected error: ${e.message}")
                onError("Unexpected error: ${e.message}")
            }
        }
    }

    fun addAppointments(
        appointmentRequest: Appointments,
        id: String,
        onResult: (Boolean, String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result = apiService.addAppointments(appointmentRequest, id)

                result.onSuccess { response ->
                    Log.d("Appointments", "Appointment booked successfully: $response")
                    onResult(true, "Success")
                }.onFailure { error ->
                    Log.e("Appointments", "Error booking appointment: ${error.message}", error)
                    onResult(false, error.message ?: "Unknown error")
                }

            } catch (e: Exception) {
                Log.e("Appointments", "Exception while booking appointment", e)
                onResult(false, e.message ?: "Unknown exception")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAppointments(id: String) {
        viewModelScope.launch {
            try {
                val result = apiService.getAppointments(id)
                result.onSuccess { data ->
                    val today = LocalDate.now()
                    val formatter =
                        DateTimeFormatter.ofPattern("yyyy-MM-dd") // Ensure correct format

                    val filteredSortedData = data
                        .map {
                            it.copy(
                                date = LocalDate.parse(it.date, formatter).toString()
                            )
                        } // Convert to LocalDate
                        .filter { LocalDate.parse(it.date) >= today } // Compare as LocalDate
                        .sortedBy { LocalDate.parse(it.date) }
                        .sortedBy { it.time }

                    _appointments.value = filteredSortedData
                }.onFailure { e ->
                    Log.e("Appointments", "Error fetching appointments", e)
                }
            } catch (e: Exception) {
                Log.e("Appointments", "Unexpected error", e)
            }
        }
    }


    fun getMedication(id: String) {
        viewModelScope.launch {
            try {
                val result = apiService.getMedications(id)
                result.onSuccess { data ->
                    _medications.value = data  // Store the fetched data in StateFlow
                }.onFailure { e ->
                    Log.e("Medications", "Error fetching medications", e)
                }
            } catch (e: Exception) {
                Log.e("Medications", "Unexpected error", e)
            }
        }
    }

    fun oldMedications(userId: String) {
        viewModelScope.launch {
            try {
                val result = apiService.oldMedications(userId)
                result.onSuccess { data ->
                    _oldMedications.value = data
                }.onFailure { e ->
                    Log.e("Medications", "Error fetching medications", e)
                }
            } catch (e: Exception) {
                Log.e("Medications", "Unexpected error", e)
            }
        }
    }


    fun getTodaysAppointments(docId: String) {
        viewModelScope.launch {
            try {
                val result = apiService.getTodaysAppointments(docId)
                result.onSuccess { data ->
                    _todaysAppointments.value =
                        data.sortedBy { it.queueIndex } // Sorting by queueIndex
                }.onFailure { e ->
                    Log.e("Appointments", "Error fetching today's appointments", e)
                }
            } catch (e: Exception) {
                Log.e("Appointments", "Error fetching today's appointments", e)
            }
        }
    }

    fun getTodaysAbsentAppointments(docId: String) {
        viewModelScope.launch {
            try {
                val result = apiService.getTodaysAbsentAppointments(docId)
                result.onSuccess { data ->
                    _absentAppointments.value = data.sortedBy { it.queueIndex }
                }.onFailure { e ->
                    Log.e("Appointments", "Error fetching today's absent appointments", e)
                }
            } catch (e: Exception) {
                Log.e("Appointments", "Error fetching today's absent appointments", e)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getPastAppointments(doctorId: String) {
        viewModelScope.launch {
            try {
                val result = apiService.getPastAppointments(doctorId)
                result.onSuccess { data ->
                    val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Input format
                    val formatterOutput =
                        DateTimeFormatter.ofPattern("dd/MM/yyyy") // Desired output format

                    _pastAppointments.value = data.map { appointment ->
                        appointment.copy(
                            date = LocalDate.parse(appointment.date, formatterInput)
                                .format(formatterOutput)
                        )
                    }
                        .sortedBy { it.queueIndex } // Sort inside each date group by queueIndex
                        .sortedByDescending { it.date } // Sort by date in descending order
                }.onFailure { e ->
                    Log.e("Appointments", "Error fetching past appointments", e)
                }
            } catch (e: Exception) {
                Log.e("Appointments", "Error fetching past appointments", e)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getFutureAppointments(doctorId: String) {
        viewModelScope.launch {
            try {
                val result = apiService.getFutureAppointments(doctorId)
                result.onSuccess { data ->
                    val formatterInput =
                        DateTimeFormatter.ofPattern("yyyy-MM-dd") // Assuming input format
                    val formatterOutput =
                        DateTimeFormatter.ofPattern("dd/MM/yyyy") // Desired format

                    _futureAppointments.value = data.map { appointment ->
                        appointment.copy(
                            date = LocalDate.parse(appointment.date, formatterInput)
                                .format(formatterOutput)
                        )
                    }.sortedBy { it.date }.sortedBy { it.queueIndex }
                }.onFailure { e ->
                    Log.e("Appointments", "Error fetching future appointments", e)
                }
            } catch (e: Exception) {
                Log.e("Appointments", "Error fetching future appointments", e)
            }
        }
    }


    fun markAppointmentAsDone(appointment: AppointmentDTO) {
        viewModelScope.launch {
            try {
                val isSuccess = apiService.markAppointmentAsDone(appointment.id)
                if (isSuccess) {
                    appointment.status = AppointmentStatus.COMPLETED  // ✅ Just change status
                }
            } catch (e: Exception) {
                Log.e("Appointments", "Error marking appointment as done", e)
            }
        }
    }

    fun markAppointmentAsAbsent(appointment: AppointmentDTO) {
        viewModelScope.launch {
            try {
                val isSuccess = apiService.markAppointmentAsAbsent(appointment.id)
                if (isSuccess) {
                    appointment.status = AppointmentStatus.ABSENT  // ✅ Just change status
                }
            } catch (e: Exception) {
                Log.e("Appointments", "Error marking appointment as absent", e)
            }
        }
    }


    fun doctorMedication(doctorId: String, userId: String) {
        viewModelScope.launch {
            try {
                val result = apiService.doctorMedication(doctorId, userId)
                result.onSuccess { data ->
                    _docMedications.value = data
                }.onFailure { e ->
                    Log.e("Medications", "Error fetching Medications", e)
                }
            } catch (e: Exception) {
                Log.e("Medications", "Error fetching Medications", e)
            }
        }
    }

    fun removeMedication(medId: String) {
        viewModelScope.launch {
            _isRemovingMedication.value = true
            try {
                apiService.removeMedications(medId)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            } finally {
                _isRemovingMedication.value = false
            }
        }
    }

    fun getReports(id: String) {
        viewModelScope.launch {
            try {
                val result = apiService.getReports(id)
                result.onSuccess { data ->
                    _reports.value = data  // Store the fetched data in StateFlow
                }.onFailure { e ->
                    Log.e("Reports", "Error fetching medications", e)
                }
            } catch (e: Exception) {
                Log.e("Reports", "Unexpected error", e)
            }
        }
    }

    fun getRecords(id: String) {
        viewModelScope.launch {
            try {
                val result = apiService.getRecords(id)
                result.onSuccess { data ->
                    _records.value = data  // Store the fetched data in StateFlow
                }.onFailure { e ->
                    Log.e("Records", "Error fetching medications", e)
                }
            } catch (e: Exception) {
                Log.e("Records", "Unexpected error", e)
            }
        }
    }

    fun downloadAndOpenPdf(recordId: String? = null, reportId: String? = null, context: Context) {
        viewModelScope.launch {
            try {
                val result = when {
                    reportId != null -> apiService.getReportFile(reportId)
                    recordId != null -> apiService.getRecordFile(recordId)
                    else -> return@launch // Exit if both are null
                }

                result.onSuccess { pdfData ->
                    val file = File(context.getExternalFilesDir(null), "report.pdf")
                    file.writeBytes(pdfData)

                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        file
                    )

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(uri, "application/pdf")
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    }

                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "No PDF viewer found!", Toast.LENGTH_LONG).show()
                    }
                }.onFailure { e ->
                    Log.e("API", "Error fetching PDF", e)
                }
            } catch (e: Exception) {
                Log.e("PDF", "Unexpected error", e)
            }
        }
    }

    fun exportPdf(reportId: String? = null, recordId: String? = null, context: Context) {
        viewModelScope.launch {
            val result = when {
                reportId != null -> apiService.getReportFile(reportId)
                recordId != null -> apiService.getRecordFile(recordId)
                else -> return@launch // Exit if both are null
            }
            result.onSuccess { pdfData ->
                try {
                    val downloadsDir =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val file = File(downloadsDir, "Report_$reportId.pdf")
                    file.writeBytes(pdfData)

                    // Show success message
                    Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_SHORT).show()

                    // Optional: Open Share Intent
                    sharePdf(file, context)
                } catch (e: Exception) {
                    Log.e("ExportPDF", "Error saving PDF", e)
                }
            }.onFailure { e ->
                Log.e("ExportPDF", "Error fetching PDF", e)
            }
        }
    }

    private fun sharePdf(file: File, context: Context) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share PDF via"))
    }
}
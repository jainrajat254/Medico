
package com.example.medico.common.koin

import android.util.Log
import com.example.medico.common.dto.EditPassword
import com.example.medico.common.model.LoginCredentials
import com.example.medico.doctor.dto.DoctorDTO
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
import com.example.medico.user.dto.UserDTO
import com.example.medico.user.model.Appointments
import com.example.medico.user.model.ExtraDetails
import com.example.medico.user.model.Medications
import com.example.medico.user.model.Records
import com.example.medico.user.model.Reports
import com.example.medico.user.model.UserDetails
import com.example.medico.user.responses.AppointmentsResponse
import com.example.medico.user.responses.MedicationResponse
import com.example.medico.user.responses.RecordsResponse
import com.example.medico.user.responses.ReportsResponse
import com.example.medico.user.responses.UserDetailsResponse
import com.example.medico.user.responses.UserLoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class ApiServiceImpl(private val client: HttpClient) : ApiService {

    private val url = "https://2bc2-2409-40d2-10bd-a157-a12b-2090-73ae-1094.ngrok-free.app"

    override suspend fun login(user: LoginCredentials): Result<UserLoginResponse> {
        return try {
            val response: UserLoginResponse = client.post("$url/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginCredentials(user.email, user.password, user.role))
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            throw Exception("Error during login: ${e.message}") // Clear error message
        }
    }

    override suspend fun registerUser(data: UserDetails): UserDetails {
        return try {
            val response: UserDetails = client.post("$url/u/register") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during registration: ${e.message}")
        }
    }

    override suspend fun registerUser(data: DoctorDetails): DoctorDetails {
        return try {
            val response: DoctorDetails = client.post("$url/doctor/register") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during registration: ${e.message}")
        }
    }

    override suspend fun loginDoc(user: LoginCredentials): DoctorLoginResponse {
        return try {
            val response: DoctorLoginResponse = client.post("$url/login") {
                contentType(ContentType.Application.Json)
                setBody(
                    LoginCredentials(
                        user.email,
                        user.password,
                        user.role
                    )
                )
            }.body()
            response
        } catch (e: Exception) {
            throw Exception("Error during login: ${e.message}")
        }
    }

    override suspend fun editDetails(data: EditUserPersonalDetails, id: String): Result<UserDetails> {
        return try {
            val response: UserDetails = client.put("$url/u/editDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editDocPersonalDetails(
        data: EditDocPersonalDetails,
        id: String,
    ): Result<DoctorLoginResponse> {
        return try {
            val response: DoctorLoginResponse = client.put("$url/doctor/editDocPersonalDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editPassword(data: EditPassword, id: String): Result<String> {
        return try {
            val response: HttpResponse = client.put("$url/editPassword/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }

            if (response.status == HttpStatusCode.OK) {
                val message = response.body<String>()  // Expecting plain string response
                Result.success(message)
            } else {
                val error = response.body<String>()  // Expecting plain string for errors too
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editDocAddressDetails(
        data: EditDocAddressDetails,
        id: String,
    ): Result<DoctorLoginResponse> {
        return try {
            val response: DoctorLoginResponse = client.put("$url/doctor/editDocAddressDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editDocMedicalDetails(
        data: EditDocMedicalDetails,
        id: String,
    ): Result<DoctorLoginResponse> {
        return try {
            val response: DoctorLoginResponse = client.put("$url/doctor/editDocMedicalDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addExtraDetails(data: ExtraDetails, id: String): Result<UserDetailsResponse> {
        return try {
            val response: UserDetailsResponse = client.put("$url/personalInfo/extraDetails/$id") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
            Log.d("data", "$data  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getExtraDetails(
        id: String,
    ): Result<UserDetailsResponse> {
        return try {
            val response: UserDetailsResponse = client.get("$url/personalInfo/getPersonalInfo/$id") {
                contentType(ContentType.Application.Json)
            }.body()
            Log.d("data", " $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPersonalInfoId(id: String): Result<String> {
        return try {
            val response: String = client.get("$url/personalInfo/getPersonalInfoId/$id") {
                contentType(ContentType.Application.Json)
            }.body()

            Log.d("data", "$response  $id")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDoctors(): Result<List<DoctorDTO>> {
        return try {
            val response: List<DoctorDTO> = client.get("$url/doctor/getDoctors") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDetails(id: String): Result<UserDTO> {
        return try {
            val response: UserDTO = client.get("$url/u/getDetails/$id") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addAppointments(request: Appointments, id: String): Result<Appointments> {
        return try {
            val response: Appointments = client.post("$url/appointments/addAppointments/$id") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addMedications(request: Medications, id: String): Result<Medications> {
        return try {
            val response: Medications = client.post("$url/medications/addMedication/$id") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMedications(id: String): Result<List<MedicationResponse>> {
        return try {
            val response: List<MedicationResponse> = client.get("$url/medications/getMedication/$id") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addReports(request: Reports, id: String): Result<Reports> {
        return try {
            val response: Reports = client.post("$url/reports/addReport/$id") {
                setBody(MultiPartFormDataContent(
                    formData {
                        append("reportName", request.reportName)
                        append("reviewedBy", request.reviewedBy)
                        append("attentionLevel", request.attentionLevel)
                        append("reportFile", request.reportFile, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=report.pdf")
                        })
                    }
                ))
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getReports(id: String): Result<List<ReportsResponse>> {
        return try {
            val response: List<ReportsResponse> = client.get("$url/reports/getReports/$id") {
                contentType(ContentType.Application.Json)
            }.body<List<ReportsResponse>>() // Explicitly specify List

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getReportFile(reportId: String): Result<ByteArray> {
        return try {
            val response: ByteArray = client.get("$url/reports/getReportFile/$reportId") {
                contentType(ContentType.Application.OctetStream)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDoctorAppointments(doctorId: String): Result<List<AppointmentDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun doctorMedication(doctorId: String, userId: String): Result<List<MedicationsDTO>> {
        return try {
            val response: List<MedicationsDTO> = client.get("$url/medications/doctorMedication/$doctorId/$userId") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateMedication(
        medId: String,
        data: Medications,
    ): Result<Medications> {
        return try {
            val response: Medications = client.put("$url/medications/updateMedication/$medId") {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()

            Log.d("data", "$data  $medId")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeMedications(medId: String): Result<String> {
        return try {
            val response: String = client.delete("$url/medications/remove/$medId") {
                contentType(ContentType.Application.Json)
            }.body()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun oldMedications(userId: String): Result<List<OldMedicationsDTO>> {
        return try {
            val response: List<OldMedicationsDTO> = client.get("$url/medications/oldMedications/$userId") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getAppointments(id: String): Result<List<AppointmentsResponse>> {
        return try {
            val response: List<AppointmentsResponse> = client.get("$url/appointments/getAppointments/$id") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTodaysAppointments(doctorId: String): Result<List<AppointmentDTO>> {
        return try {
            val response: List<AppointmentDTO> = client.get("$url/appointments/today/$doctorId") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTodaysAbsentAppointments(doctorId: String): Result<List<AppointmentDTO>> {
        return try {
            val response: List<AppointmentDTO> = client.get("$url/appointments/today/absent/$doctorId") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPastAppointments(doctorId: String): Result<List<AppointmentDTO>> {
        return try {
            val response: List<AppointmentDTO> = client.get("$url/appointments/past/$doctorId") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFutureAppointments(doctorId: String): Result<List<AppointmentDTO>> {
        return try {
            val response: List<AppointmentDTO> = client.get("$url/appointments/future/$doctorId") {
                contentType(ContentType.Application.Json)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun markAppointmentAsDone(appointmentId: String): Boolean {
        return try {
            val response = client.patch("$url/appointments/$appointmentId/done") {
                contentType(ContentType.Application.Json)
                setBody(AppointmentStatusRequest(AppointmentStatus.COMPLETED)) // ✅ Send ENUM
            }
            Log.d("API_RESPONSE", "Response Code: ${response.status}")
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error marking appointment as done", e)
            false
        }
    }

    override suspend fun markAppointmentAsAbsent(appointmentId: String): Boolean {
        return try {
            val response = client.patch("$url/appointments/$appointmentId/absent") {
                contentType(ContentType.Application.Json)
                setBody(AppointmentStatusRequest(AppointmentStatus.ABSENT)) // ✅ Send ENUM
            }
            Log.d("API_RESPONSE", "Response Code: ${response.status}")
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error marking appointment as absent", e)
            false
        }
    }

    override suspend fun addRecords(record: Records, userId: String): Result<Records> {
        return try {
            val response: Records = client.post("$url/records/addRecord/$userId") {
                setBody(MultiPartFormDataContent(
                    formData {
                        append("recordName", record.recordName)
                        append("reviewedBy", record.reviewedBy)
                        append("review", record.review)
                        append("recordFile", record.recordFile, Headers.build {
                            append(HttpHeaders.ContentType, "application/pdf")
                            append(HttpHeaders.ContentDisposition, "filename=record.pdf")
                        })
                    }
                ))
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecords(id: String): Result<List<RecordsResponse>> {
        return try {
            val response: List<RecordsResponse> = client.get("$url/records/getRecord/$id") {
                contentType(ContentType.Application.Json)
            }.body<List<RecordsResponse>>() // Explicitly specify List

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getRecordFile(recordId: String): Result<ByteArray> {
        return try {
            val response: ByteArray = client.get("$url/records/getRecordFile/$recordId") {
                contentType(ContentType.Application.OctetStream)
            }.body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@Serializable
data class AppointmentStatusRequest(val status: AppointmentStatus) // ✅ Now uses Enum

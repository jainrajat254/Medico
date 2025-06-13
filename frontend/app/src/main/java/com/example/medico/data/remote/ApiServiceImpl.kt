package com.example.medico.data.remote

import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.AppointmentStatus
import com.example.medico.domain.model.Appointments
import com.example.medico.domain.model.AppointmentsResponse
import com.example.medico.domain.model.DoctorDTO
import com.example.medico.domain.model.DoctorDetails
import com.example.medico.domain.model.DoctorLoginResponse
import com.example.medico.domain.model.EditDocAddressDetails
import com.example.medico.domain.model.EditDocMedicalDetails
import com.example.medico.domain.model.EditDocPersonalDetails
import com.example.medico.domain.model.EditPassword
import com.example.medico.domain.model.EditUserPersonalDetails
import com.example.medico.domain.model.ExtraDetails
import com.example.medico.domain.model.GetExtraDetailsId
import com.example.medico.domain.model.LoginCredentials
import com.example.medico.domain.model.MedicationResponse
import com.example.medico.domain.model.Medications
import com.example.medico.domain.model.MedicationsDTO
import com.example.medico.domain.model.OldMedicationsDTO
import com.example.medico.domain.model.Records
import com.example.medico.domain.model.RecordsResponse
import com.example.medico.domain.model.Reports
import com.example.medico.domain.model.ReportsResponse
import com.example.medico.domain.model.UserDTO
import com.example.medico.domain.model.UserDetails
import com.example.medico.domain.model.UserDetailsResponse
import com.example.medico.domain.model.UserLoginResponse
import com.example.medico.domain.service.ApiService
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
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class ApiServiceImpl(private val client: HttpClient) : ApiService {

    private val BASE_URL = " https://b773-2409-40d2-1027-f3fe-1c47-4c-6f76-90aa.ngrok-free.app"

    override suspend fun login(loginRequest: LoginCredentials): UserLoginResponse {
        return client.post("$BASE_URL/login") {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }

    override suspend fun registerUser(data: UserDetails): UserDetails {
        return client.post("$BASE_URL/u/register") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body()
    }

    override suspend fun registerDoc(data: DoctorDetails): DoctorDetails {
        return client.post("$BASE_URL/doctor/register") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body()
    }

    override suspend fun loginDoc(user: LoginCredentials): DoctorLoginResponse {
        return client.post("$BASE_URL/login") {
            contentType(ContentType.Application.Json)
            setBody(
                LoginCredentials(
                    user.email,
                    user.password,
                    user.role
                )
            )
        }.body()
    }

    override suspend fun editDetails(
        data: EditUserPersonalDetails,
        id: String,
    ): UserDetails {
        return client.put("$BASE_URL/u/editDetails/$id") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body()
    }

    override suspend fun editDocPersonalDetails(
        data: EditDocPersonalDetails,
        id: String,
    ): DoctorLoginResponse {
        return client.put("$BASE_URL/doctor/editDocPersonalDetails/$id") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body()
    }

    override suspend fun editPassword(data: EditPassword, id: String): String {
        return client.put("$BASE_URL/editPassword/$id") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body()
    }

    override suspend fun editDocAddressDetails(
        id: String,
        data: EditDocAddressDetails,
    ): DoctorLoginResponse {
        return client.put("$BASE_URL/doctor/editDocAddressDetails/$id") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body()
    }

    override suspend fun editDocMedicalDetails(
        data: EditDocMedicalDetails,
        id: String,
    ): DoctorLoginResponse {
        return client.put("$BASE_URL/doctor/editDocMedicalDetails/$id") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body()
    }

    override suspend fun addExtraDetails(
        data: ExtraDetails,
        id: String,
    ): UserDetailsResponse {
        return client.put("$BASE_URL/personalInfo/extraDetails/$id") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body()
    }

    override suspend fun getExtraDetails(
        id: String,
    ): UserDetailsResponse {
        return client.get("$BASE_URL/personalInfo/getPersonalInfo/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun getPersonalInfoId(id: String): GetExtraDetailsId {
        return client.get("$BASE_URL/personalInfo/getPersonalInfoId/$id") {
            contentType(ContentType.Application.Json)
        }.body()

    }

    override suspend fun getDoctors(): List<DoctorDTO> {
        return client.get("$BASE_URL/doctor/getDoctors") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun getDetails(id: String): UserDTO {
        return client.get("$BASE_URL/u/getDetails/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun addAppointments(
        request: Appointments,
        id: String,
    ): Appointments {
        return client.post("$BASE_URL/appointments/addAppointments/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun addMedications(
        request: Medications,
        id: String,
    ): Medications {
        return client.post("$BASE_URL/medications/addMedication/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun getMedications(id: String): List<MedicationResponse> {
        return client.get("$BASE_URL/medications/getMedication/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun addReports(
        request: Reports,
        id: String,
    ): Reports {
        return client.post("$BASE_URL/reports/addReport/$id") {
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
    }


    override suspend fun getReports(id: String): List<ReportsResponse> {
        return client.get("$BASE_URL/reports/getReports/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun getReportFile(reportId: String): ByteArray {
        return client.get("$BASE_URL/reports/getReportFile/$reportId") {
            contentType(ContentType.Application.OctetStream)
        }.body()
    }

    override suspend fun getDoctorAppointments(doctorId: String): List<AppointmentDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun doctorMedication(
        doctorId: String,
        userId: String,
    ): List<MedicationsDTO> {
        return client.get("$BASE_URL/medications/doctorMedication/$doctorId/$userId") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun updateMedication(
        medId: String,
        data: Medications,
    ): Medications {
        return client.put("$BASE_URL/medications/updateMedication/$medId") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body()
    }

    override suspend fun removeMedications(medId: String): String {
        return client.delete("$BASE_URL/medications/remove/$medId") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun oldMedications(userId: String): List<OldMedicationsDTO> {
        return client.get("$BASE_URL/medications/oldMedications/$userId") {
            contentType(ContentType.Application.Json)
        }.body()
    }


    override suspend fun getAppointments(id: String): List<AppointmentsResponse> {
        return client.get("$BASE_URL/appointments/getAppointments/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun getAbsentAppointmentsForToday(doctorId: String): List<AppointmentDTO> {
        return client.get("$BASE_URL/appointments/today/$doctorId") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun getAppointmentsForToday(doctorId: String): List<AppointmentDTO> {
        return client.get("$BASE_URL/appointments/today/absent/$doctorId") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun getPastAppointments(doctorId: String): List<AppointmentDTO> {
        return client.get("$BASE_URL/appointments/past/$doctorId") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun getFutureAppointments(doctorId: String): List<AppointmentDTO> {
        return client.get("$BASE_URL/appointments/future/$doctorId") {
            contentType(ContentType.Application.Json)
        }.body()
    }


    override suspend fun markAppointmentAsDone(appointmentId: String): Boolean {
        return client.patch("$BASE_URL/appointments/$appointmentId/done") {
            contentType(ContentType.Application.Json)
            setBody(AppointmentStatusRequest(AppointmentStatus.COMPLETED))
        }.body()
    }

    override suspend fun markAppointmentAsAbsent(appointmentId: String): Boolean {
        return client.patch("$BASE_URL/appointments/$appointmentId/absent") {
            contentType(ContentType.Application.Json)
            setBody(AppointmentStatusRequest(AppointmentStatus.ABSENT)) // ✅ Send ENUM
        }.body()
    }

    override suspend fun addRecords(
        record: Records,
        userId: String,
    ): Records {
        return client.post("$BASE_URL/records/addRecord/$userId") {
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
    }

    override suspend fun getRecords(id: String): List<RecordsResponse> {
        return client.get("$BASE_URL/records/getRecord/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }


    override suspend fun getRecordFile(recordId: String): ByteArray {
        return client.get("$BASE_URL/records/getRecordFile/$recordId") {
            contentType(ContentType.Application.OctetStream)
        }.body()
    }
}

@Serializable
data class AppointmentStatusRequest(val status: AppointmentStatus)

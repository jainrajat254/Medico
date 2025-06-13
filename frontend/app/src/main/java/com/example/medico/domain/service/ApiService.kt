package com.example.medico.domain.service

import com.example.medico.domain.model.AppointmentDTO
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

interface ApiService {
    suspend fun login(loginRequest: LoginCredentials): UserLoginResponse
    suspend fun registerUser(data: UserDetails): UserDetails
    suspend fun loginDoc(user: LoginCredentials): DoctorLoginResponse
    suspend fun registerDoc(data: DoctorDetails): DoctorDetails

    suspend fun addAppointments(request: Appointments, id: String): Appointments
    suspend fun getAppointments(id: String): List<AppointmentsResponse>
    suspend fun markAppointmentAsDone(appointmentId: String): Boolean
    suspend fun markAppointmentAsAbsent(appointmentId: String): Boolean
    suspend fun getAppointmentsForToday(doctorId: String): List<AppointmentDTO>
    suspend fun getAbsentAppointmentsForToday(doctorId: String): List<AppointmentDTO>
    suspend fun getPastAppointments(doctorId: String): List<AppointmentDTO>
    suspend fun getFutureAppointments(doctorId: String): List<AppointmentDTO>
    suspend fun getDoctorAppointments(doctorId: String): List<AppointmentDTO>

    suspend fun addMedications(request: Medications, id: String): Medications
    suspend fun getMedications(id: String): List<MedicationResponse>
    suspend fun doctorMedication(doctorId: String, userId: String): List<MedicationsDTO>
    suspend fun updateMedication(medId: String, data: Medications): Medications
    suspend fun removeMedications(medId: String): String
    suspend fun oldMedications(userId: String): List<OldMedicationsDTO>

    suspend fun addReports(request: Reports, id: String): Reports
    suspend fun getReports(id: String): List<ReportsResponse>
    suspend fun getReportFile(reportId: String): ByteArray

    suspend fun addRecords(record: Records, userId: String): Records
    suspend fun getRecords(id: String): List<RecordsResponse>
    suspend fun getRecordFile(recordId: String): ByteArray


    suspend fun editDetails(data: EditUserPersonalDetails, id: String): UserDetails
    suspend fun editDocPersonalDetails(
        data: EditDocPersonalDetails,
        id: String,
    ): DoctorLoginResponse

    suspend fun editPassword(data: EditPassword, id: String): String
    suspend fun editDocAddressDetails(
        id: String,
        data: EditDocAddressDetails,
    ): DoctorLoginResponse

    suspend fun editDocMedicalDetails(
        data: EditDocMedicalDetails,
        id: String,
    ): DoctorLoginResponse

    suspend fun addExtraDetails(data: ExtraDetails, id: String): UserDetailsResponse
    suspend fun getExtraDetails(id: String): UserDetailsResponse
    suspend fun getPersonalInfoId(id: String): GetExtraDetailsId
    suspend fun getDoctors(): List<DoctorDTO>
    suspend fun getDetails(id: String): UserDTO

}

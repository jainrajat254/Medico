package com.example.medico.common.koin

import com.example.medico.common.dto.EditPassword
import com.example.medico.common.model.LoginCredentials
import com.example.medico.doctor.dto.DoctorDTO
import com.example.medico.doctor.dto.EditDocAddressDetails
import com.example.medico.doctor.dto.EditDocMedicalDetails
import com.example.medico.doctor.dto.EditDocPersonalDetails
import com.example.medico.doctor.model.DoctorDetails
import com.example.medico.doctor.responses.DoctorLoginResponse
import com.example.medico.user.dto.AppointmentDTO
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

interface ApiService {
    suspend fun login(user: LoginCredentials): Result<UserLoginResponse>
    suspend fun registerUser(data: UserDetails): UserDetails
    suspend fun loginDoc(user: LoginCredentials): DoctorLoginResponse
    suspend fun registerUser(data: DoctorDetails): DoctorDetails
    suspend fun editDetails(data: EditUserPersonalDetails, id: String): Result<UserDetails>
    suspend fun editDocPersonalDetails(
        data: EditDocPersonalDetails,
        id: String,
    ): Result<DoctorLoginResponse>

    suspend fun editPassword(data: EditPassword, id: String): Result<UserDetails>
    suspend fun editDocAddressDetails(
        data: EditDocAddressDetails,
        id: String,
    ): Result<DoctorLoginResponse>

    suspend fun editDocMedicalDetails(
        data: EditDocMedicalDetails,
        id: String,
    ): Result<DoctorLoginResponse>

    suspend fun addExtraDetails(data: ExtraDetails, id: String): Result<UserDetailsResponse>
    suspend fun getExtraDetails(id: String): Result<UserDetailsResponse>
    suspend fun getPersonalInfoId(id: String): Result<String>
    suspend fun getDoctors(): Result<List<DoctorDTO>>
    suspend fun getDetails(id: String): Result<UserDTO>
    suspend fun addAppointments(request: Appointments, id: String): Result<Appointments>
    suspend fun getAppointments(id: String): Result<List<AppointmentsResponse>>
    suspend fun addMedications(request: Medications, id: String): Result<Medications>
    suspend fun getMedications(id: String): Result<List<MedicationResponse>>
    suspend fun addReports(request: Reports, id: String): Result<Reports>
    suspend fun getReports(id: String): Result<List<ReportsResponse>>
    suspend fun getReportFile(reportId: String): Result<ByteArray>
    suspend fun getDoctorAppointments(doctorId: String): Result<List<AppointmentDTO>>
    suspend fun doctorMedication(doctorId: String, userId: String): Result<List<MedicationsDTO>>
    suspend fun updateMedication(medId: String, data: Medications): Result<Medications>
    suspend fun removeMedications(medId: String): Result<String>
    suspend fun oldMedications(userId: String): Result<List<OldMedicationsDTO>>

    suspend fun markAppointmentAsDone(appointmentId: String): Boolean
    suspend fun markAppointmentAsAbsent(appointmentId: String): Boolean
    suspend fun getTodaysAppointments(doctorId: String): Result<List<AppointmentDTO>>
    suspend fun getTodaysAbsentAppointments(doctorId: String): Result<List<AppointmentDTO>>
    suspend fun getPastAppointments(doctorId: String): Result<List<AppointmentDTO>>
    suspend fun getFutureAppointments(doctorId: String): Result<List<AppointmentDTO>>
    suspend fun addRecords(record: Records, userId: String): Result<Records>
    suspend fun getRecords(id: String): Result<List<RecordsResponse>>
    suspend fun getRecordFile(recordId: String): Result<ByteArray>

}

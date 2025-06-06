package com.example.medico.domain.repository

import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.Appointments
import com.example.medico.domain.model.AppointmentsResponse

interface AppointmentsRepository {

    suspend fun addAppointments(request: Appointments, id: String): Appointments

    suspend fun getAppointments(id: String): List<AppointmentsResponse>

    suspend fun markAppointmentAsDone(appointmentId: String): Boolean

    suspend fun markAppointmentAsAbsent(appointmentId: String): Boolean

    suspend fun getAppointmentsForToday(doctorId: String): List<AppointmentDTO>

    suspend fun getAbsentAppointmentsForToday(doctorId: String): List<AppointmentDTO>

    suspend fun getPastAppointments(doctorId: String): List<AppointmentDTO>

    suspend fun getFutureAppointments(doctorId: String): List<AppointmentDTO>

    suspend fun getDoctorAppointments(doctorId: String): List<AppointmentDTO>
}
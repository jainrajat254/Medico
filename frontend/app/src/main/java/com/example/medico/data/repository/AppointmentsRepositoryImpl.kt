package com.example.medico.data.repository

import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.Appointments
import com.example.medico.domain.model.AppointmentsResponse
import com.example.medico.domain.repository.AppointmentsRepository
import com.example.medico.domain.service.ApiService

class AppointmentsRepositoryImpl(private val apiService: ApiService) : AppointmentsRepository {
    override suspend fun addAppointments(request: Appointments, id: String): Appointments {
        return apiService.addAppointments(request = request, id = id)
    }

    override suspend fun getAppointments(id: String): List<AppointmentsResponse> {
        return apiService.getAppointments(id)
    }

    override suspend fun markAppointmentAsDone(appointmentId: String): Boolean {
        return apiService.markAppointmentAsDone(appointmentId)
    }

    override suspend fun markAppointmentAsAbsent(appointmentId: String): Boolean {
        return apiService.markAppointmentAsAbsent(appointmentId)
    }

    override suspend fun getAppointmentsForToday(doctorId: String): List<AppointmentDTO> {
        return apiService.getAbsentAppointmentsForToday(doctorId)
    }

    override suspend fun getAbsentAppointmentsForToday(doctorId: String): List<AppointmentDTO> {
        return apiService.getAppointmentsForToday(doctorId)
    }

    override suspend fun getPastAppointments(doctorId: String): List<AppointmentDTO> {
        return apiService.getPastAppointments(doctorId)
    }

    override suspend fun getFutureAppointments(doctorId: String): List<AppointmentDTO> {
        return apiService.getFutureAppointments(doctorId)
    }

    override suspend fun getDoctorAppointments(doctorId: String): List<AppointmentDTO> {
        return apiService.getDoctorAppointments(doctorId)
    }

}
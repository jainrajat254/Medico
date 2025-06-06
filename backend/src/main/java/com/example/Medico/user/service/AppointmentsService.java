package com.example.Medico.user.service;

import com.example.Medico.user.dto.AppointmentDTO;
import com.example.Medico.user.model.Appointments;
import com.example.Medico.user.model.Users;
import com.example.Medico.user.repository.AppointmentsRepository;
import com.example.Medico.user.repository.UserRepository;
import com.example.Medico.user.responses.AppointmentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    // Add a new appointment with default status as "booked"
    public Appointments addAppointments(AppointmentsResponse appointmentsResponse, UUID id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate appointmentDate = LocalDate.parse(appointmentsResponse.getDate(), formatter);
        int queueNumber = appointmentsRepository.countAppointmentsForDoctorOnDate(appointmentDate, appointmentsResponse.getDoctorId()) + 1;

        Appointments appointment = new Appointments();
        appointment.setUsers(user);
        appointment.setDoctorId(appointmentsResponse.getDoctorId());
        appointment.setDoctorName(appointmentsResponse.getDoctorName());
        appointment.setPatientName(appointmentsResponse.getPatientName());
        appointment.setSpecialization(appointmentsResponse.getSpecialization());
        appointment.setWorkspaceName(appointmentsResponse.getWorkspaceName());
        appointment.setDate(appointmentDate);
        appointment.setTime(appointmentsResponse.getTime());
        appointment.setStatus(Appointments.AppointmentStatus.BOOKED);
        appointment.setQueueIndex(queueNumber); // Assign queue index
        return appointmentsRepository.save(appointment);
    }

    // Get all appointments for a user
    public List<Appointments> getAppointments(UUID id) {
        return appointmentsRepository.findByUsers_Id(id);
    }

    // Get today's appointments that are still "booked"
    public List<AppointmentDTO> getTodaysAppointments(UUID doctorId) {
        return appointmentsRepository.findTodaysAppointments(doctorId, Appointments.AppointmentStatus.BOOKED);
    }

    // Get past appointments that are "completed"
    public List<AppointmentDTO> getPastAppointments(UUID doctorId) {
        return appointmentsRepository.findPastAppointments(doctorId);
    }

    // Get future appointments that are still "booked"
    public List<AppointmentDTO> getFutureAppointments(UUID doctorId) {
        return appointmentsRepository.findFutureAppointments(doctorId);
    }

    // Update the status of an appointment (booked -> completed / reschedule)
    public boolean updateAppointmentStatus(UUID id, String status) {
        Optional<Appointments> appointmentOpt = appointmentsRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointments appointment = appointmentOpt.get();

            try {
                Appointments.AppointmentStatus newStatus = Appointments.AppointmentStatus.valueOf(status.toUpperCase());
                appointment.setStatus(newStatus);
                appointmentsRepository.save(appointment);
                return true;
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid appointment status: " + status);
            }
        }
        return false;
    }

    public List<AppointmentDTO> getTodaysAbsentAppointments(UUID doctorId) {
        return appointmentsRepository.findTodaysAbsentAppointments(doctorId, Appointments.AppointmentStatus.ABSENT);

    }
}

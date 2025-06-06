package com.example.Medico.user.repository;

import com.example.Medico.user.dto.AppointmentDTO;
import com.example.Medico.user.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, UUID> {

    List<Appointments> findByUsers_Id(UUID id);

    // Get today's appointments (only booked)
    @Query("SELECT new com.example.Medico.user.dto.AppointmentDTO(a.id, a.users.id, a.patientName, a.queueIndex,  a.date, a.time, a.appointmentBookingTime, a.status) " + "FROM Appointments a WHERE a.doctorId = :doctorId AND a.date = CURRENT_DATE AND a.status = 'BOOKED'")
    List<AppointmentDTO> findTodaysAppointments(@Param("doctorId") UUID doctorId, Appointments.AppointmentStatus booked);

    /// Get past appointments (including today) with status COMPLETED
    @Query("SELECT new com.example.Medico.user.dto.AppointmentDTO(a.id, a.users.id, a.patientName, a.queueIndex ,a.date, a.time, a.appointmentBookingTime, a.status) " + "FROM Appointments a WHERE a.doctorId = :doctorId AND a.date <= CURRENT_DATE AND a.status IN ('COMPLETED', 'ABSENT')")
    List<AppointmentDTO> findPastAppointments(@Param("doctorId") UUID doctorId);


    @Query("SELECT new com.example.Medico.user.dto.AppointmentDTO(a.id, a.users.id, a.patientName, a.queueIndex, a.date, a.time, a.appointmentBookingTime, a.status) " + "FROM Appointments a WHERE a.doctorId = :doctorId AND a.date > CURRENT_DATE AND a.status = 'BOOKED'")
    List<AppointmentDTO> findFutureAppointments(@Param("doctorId") UUID doctorId);

    @Query("SELECT new com.example.Medico.user.dto.AppointmentDTO(a.id, a.users.id, a.patientName, a.queueIndex, a.date, a.time, a.appointmentBookingTime, a.status) " + "FROM Appointments a WHERE a.doctorId = :doctorId AND a.date = CURRENT_DATE AND a.status = 'ABSENT'")
    List<AppointmentDTO> findTodaysAbsentAppointments(@Param("doctorId") UUID doctorId, Appointments.AppointmentStatus absent);

    @Query("SELECT COUNT(a) FROM Appointments a WHERE a.date = :date AND a.doctorId = :doctorId")
    int countAppointmentsForDoctorOnDate(@Param("date") LocalDate date, @Param("doctorId") UUID doctorId);

}

package com.example.Medico.user.controller;

import com.example.Medico.user.dto.AppointmentDTO;
import com.example.Medico.user.model.Appointments;
import com.example.Medico.user.responses.AppointmentsResponse;
import com.example.Medico.user.service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

    @Autowired
    private AppointmentsService appointmentsService;

    @PostMapping("/addAppointments/{id}")
    public ResponseEntity<Appointments> addAppointments(@RequestBody AppointmentsResponse appointmentsResponse, @PathVariable UUID id) {
        return ResponseEntity.ok(appointmentsService.addAppointments(appointmentsResponse, id));
    }

    @GetMapping("/getAppointments/{id}")
    public ResponseEntity<List<Appointments>> getAppointments(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentsService.getAppointments(id));
    }

    @GetMapping("/today/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getTodaysAppointments(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(appointmentsService.getTodaysAppointments(doctorId));
    }

    @GetMapping("/today/absent/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getTodaysAbsentAppointments(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(appointmentsService.getTodaysAbsentAppointments(doctorId));
    }

    @GetMapping("/past/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getPastAppointments(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(appointmentsService.getPastAppointments(doctorId));
    }

    @GetMapping("/future/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getFutureAppointments(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(appointmentsService.getFutureAppointments(doctorId));
    }

    @PatchMapping("/{id}/done")
    public ResponseEntity<String> markAppointmentAsDone(@PathVariable UUID id) {
        boolean updated = appointmentsService.updateAppointmentStatus(id, "completed");
        return updated ? ResponseEntity.ok("Appointment marked as done")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
    }

    @PatchMapping("/{id}/absent")
    public ResponseEntity<String> markAppointmentAsAbsent(@PathVariable UUID id) {
        boolean updated = appointmentsService.updateAppointmentStatus(id, "absent");
        return updated ? ResponseEntity.ok("Appointment marked as absent")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
    }
}

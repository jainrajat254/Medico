package com.example.Medico.user.service;

import com.example.Medico.user.dto.MedicationsDTO;
import com.example.Medico.user.model.Medications;
import com.example.Medico.user.model.OldMedications;
import com.example.Medico.user.model.Users;
import com.example.Medico.user.repository.MedicationsRepository;
import com.example.Medico.user.repository.OldMedicationsRepository;
import com.example.Medico.user.repository.UserRepository;
import com.example.Medico.user.responses.MedicationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicationsService {

    @Autowired
    private MedicationsRepository medicationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OldMedicationsRepository oldMedicationsRepository;

    public Medications addMedication(MedicationsResponse medicationsResponse, UUID id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String time = (medicationsResponse.getTime() == null || medicationsResponse.getTime().isEmpty()) ? null : medicationsResponse.getTime();

        Medications medication = new Medications(
                user,
                medicationsResponse.getDoctorId(),
                medicationsResponse.getDoctorName(),
                medicationsResponse.getMedicationName(),
                medicationsResponse.getDosageType(),
                medicationsResponse.getMedicationType(),
                medicationsResponse.getFrequency(),
                medicationsResponse.getDuration(),
                medicationsResponse.getIntakeMethod(),
                time
        );

        return medicationsRepository.save(medication);
    }


    public List<Medications> getMedications(UUID id) {
        return medicationsRepository.findByUsers_Id(id);
    }

    public List<MedicationsDTO> doctorMedication(UUID doctorId, UUID userId) {
        return medicationsRepository.findByDoctorIdAndUserId(doctorId,userId);
    }

    public Medications updateMedication(UUID medId, Medications updatedMedication) {
        Optional<Medications> existingMedicationOpt = medicationsRepository.findById(medId);

        if (existingMedicationOpt.isPresent()) {
            Medications existingMedication = existingMedicationOpt.get();

            if (updatedMedication.getMedicationName() != null) {
                existingMedication.setMedicationName(updatedMedication.getMedicationName());
            }
            if (updatedMedication.getDosageType() != null) {
                existingMedication.setDosageType(updatedMedication.getDosageType());
            }
            if (updatedMedication.getTime() != null) {
                existingMedication.setTime(updatedMedication.getTime());
            }
            if (updatedMedication.getFrequency() != null) {
                existingMedication.setFrequency(updatedMedication.getFrequency());
            }
            if (updatedMedication.getDuration() != null) {
                existingMedication.setDuration(updatedMedication.getDuration());
            }
            if (updatedMedication.getIntakeMethod() != null) {
                existingMedication.setIntakeMethod(updatedMedication.getIntakeMethod());
            }
            if (updatedMedication.getMedicationType() != null) {
                existingMedication.setMedicationType(updatedMedication.getMedicationType());
            }

            return medicationsRepository.save(existingMedication); // Save updated record
        } else {
            throw new RuntimeException("Medication not found with ID: " + medId);
        }
    }

    public void removeMedication(UUID medId) {
        Optional<Medications> medicationOpt = medicationsRepository.findById(medId);

        if (medicationOpt.isPresent()) {
            Medications medication = medicationOpt.get();

            OldMedications oldMedication = new OldMedications(
                    medication.getUsers(),
                    medication.getDoctorId(),
                    medication.getDoctorName(),
                    medication.getMedicationName(),
                    medication.getStartDate()
            );

            oldMedicationsRepository.save(oldMedication); // ✅ Save in old medications

            medicationsRepository.deleteById(medId); // ✅ Delete from current medications
        } else {
            throw new RuntimeException("Medication not found with ID: " + medId);
        }
    }

    public List<OldMedications> oldMedications(UUID userId) {
        return oldMedicationsRepository.findByUserId(userId);
    }
}

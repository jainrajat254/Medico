package com.example.Medico.user.dto;

import java.util.UUID;

public class MedicationsDTO {
    private UUID id;
    private String medicationName;
    private String dosageType;  // ✅ New field
    private String medicationType;  // ✅ New field
    private String frequency;
    private String duration;  // ✅ New field
    private String intakeMethod;  // ✅ New field
    private String time;  // ✅ New field

    public MedicationsDTO(UUID id, String medicationName, String dosageType, String medicationType, String frequency, String duration, String intakeMethod, String time) {
        this.id = id;
        this.medicationName = medicationName;
        this.dosageType = dosageType;
        this.medicationType = medicationType;
        this.frequency = frequency;
        this.duration = duration;
        this.intakeMethod = intakeMethod;
        this.time = time;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosageType() {
        return dosageType;
    }

    public void setDosageType(String dosageType) {
        this.dosageType = dosageType;
    }

    public String getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(String medicationType) {
        this.medicationType = medicationType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIntakeMethod() {
        return intakeMethod;
    }

    public void setIntakeMethod(String intakeMethod) {
        this.intakeMethod = intakeMethod;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


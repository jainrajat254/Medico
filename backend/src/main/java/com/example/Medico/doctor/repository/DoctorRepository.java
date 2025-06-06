package com.example.Medico.doctor.repository;

import com.example.Medico.doctor.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorDetails, UUID> {

    DoctorDetails findByEmail(String email);

    Optional<DoctorDetails> findById(UUID id); // Find doctor by UID
}

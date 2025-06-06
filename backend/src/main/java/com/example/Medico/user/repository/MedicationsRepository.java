package com.example.Medico.user.repository;

import com.example.Medico.user.dto.MedicationsDTO;
import com.example.Medico.user.model.Medications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicationsRepository extends JpaRepository<Medications, UUID> {

    List<Medications> findByUsers_Id(UUID id);

    @Query("SELECT new com.example.Medico.user.dto.MedicationsDTO(u.id, u.medicationName, u.dosageType, u.medicationType, u.frequency, u.duration, u.intakeMethod, u.time) " +
            "FROM Medications u " +
            "WHERE u.doctorId = :doctorId AND u.users.id = :userId")
    List<MedicationsDTO> findByDoctorIdAndUserId(@Param("doctorId") UUID doctorId, @Param("userId") UUID userId);

}

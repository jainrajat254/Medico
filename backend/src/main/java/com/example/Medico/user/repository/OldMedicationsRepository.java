package com.example.Medico.user.repository;

import com.example.Medico.user.model.OldMedications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OldMedicationsRepository extends JpaRepository<OldMedications, UUID> {

    @Query("SELECT o FROM OldMedications o WHERE o.users.id = :userId")
    List<OldMedications> findByUserId(@Param("userId") UUID userId);



}

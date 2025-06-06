package com.example.Medico.user.repository;

import com.example.Medico.user.model.Records;
import com.example.Medico.user.responses.RecordsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecordsRepository extends JpaRepository<Records, UUID> {

    @Query("SELECT new com.example.Medico.user.responses.RecordsResponse(r.id, r.recordName, r.reviewedBy, r.review, r.date) FROM Records r WHERE r.users.id = :userId")
    List<RecordsResponse> findRecordsByUserId(@Param("userId") UUID userId);

}

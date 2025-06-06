package com.example.Medico.user.repository;

import com.example.Medico.user.model.Reports;
import com.example.Medico.user.model.Users;
import com.example.Medico.user.responses.ReportsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, UUID> {
    List<Reports> findByUsers_Id(UUID id);

    @Query("SELECT new com.example.Medico.user.responses.ReportsResponse(u.id, u.reportName, u.reviewedBy, u.attentionLevel, u.date) FROM Reports u WHERE u.users.id = :userId")
    List<ReportsResponse> findReportsByUserId(@Param("userId") UUID userId);

}

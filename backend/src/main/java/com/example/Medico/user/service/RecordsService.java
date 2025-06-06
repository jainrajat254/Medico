package com.example.Medico.user.service;

import com.example.Medico.user.model.Records;
import com.example.Medico.user.model.Users;
import com.example.Medico.user.repository.RecordsRepository;
import com.example.Medico.user.repository.UserRepository;
import com.example.Medico.user.responses.RecordsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RecordsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecordsRepository recordsRepository;

    @Transactional
    public Records addRecords(RecordsResponse recordsResponse, UUID id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Records record = new Records();
        record.setRecordName(recordsResponse.getRecordName());
        record.setReviewedBy(recordsResponse.getReviewedBy());
        record.setReview(recordsResponse.getReview());
        record.setDate(recordsResponse.getDate());
        record.setUsers(user);
        byte[] recordData = recordsResponse.getRecord();
        record.setRecord(recordData);
        recordsRepository.save(record);
        return record;
    }

    public List<RecordsResponse> getRecord(UUID userId) {
        return recordsRepository.findRecordsByUserId(userId);
    }


    public Records getRecordFile(UUID recordId) {
        return recordsRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

}

package com.example.dodam.repository.medicalRecord;

import com.example.dodam.domain.medicalRecord.MedicalRecord;
import com.example.dodam.domain.medicalRecord.MedicalRecordList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository {
    Integer save(MedicalRecord record);     // 진료기록 등록
    Integer update(MedicalRecord record);    // 진료기록 수정
}

package com.example.dodam.service.medicalRecord;

import com.example.dodam.domain.medicalRecord.MedicalRecord;
import com.example.dodam.domain.medicalRecord.MedicalRecordList;
import com.example.dodam.repository.medicalRecord.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    // 진료기록 등록
    public Integer save(MedicalRecord record){
        return medicalRecordRepository.save(record);
    }

    // 진료기록 수정
    public Integer update(MedicalRecord record){
        return medicalRecordRepository.update(record);
    }

}

package com.example.dodam.controller.medicalRecord;

import com.example.dodam.domain.medicalRecord.MedicalRecord;
import com.example.dodam.service.medicalRecord.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MedicalRecordControllor {
    private final MedicalRecordService medicalRecordService;
    @Autowired
    public MedicalRecordControllor(MedicalRecordService medicalRecordService){
        this.medicalRecordService = medicalRecordService;
    }

    // 진료기록 등록
    // MedicalRecord DTO 전달, id 반환
    @PostMapping("/medical-record")
    public ResponseEntity<?> addMedicalRecord(@RequestBody MedicalRecord record){
        return new ResponseEntity<>(medicalRecordService.save(record), HttpStatus.CREATED);
    }

    // 진료기록 수정
    // MedicalRecord DTO 전달, 완료 메세지 반환
    @PutMapping("/medical-record")
    public ResponseEntity<?> updateMedicalRecord(@RequestBody MedicalRecord record){
        medicalRecordService.update(record); // id 반환
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    // 진료기록 삭제
    // id 전달, 완료 메세지 반환
    @DeleteMapping("/medical-record/{id}")
    public ResponseEntity<?> deleteMedicalRecord(@PathVariable("id") Integer id){
        medicalRecordService.delete(id);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
    
}

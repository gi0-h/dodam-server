package com.example.dodam.controller.inquiry;

import com.example.dodam.domain.inquiry.Inquiry;
import com.example.dodam.service.inquiry.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    // GET 전체 게시물 조회 -> 대략적인 게시물 정보 확인
    // GET 상세 게시물 조회 -> 게시글 상세 조회
    // POST 게시글 작성
    // PUT 게시글 수정
    // DELETE 게시글 삭제


    // localhost:8080/inquiries
    // 전체 게시물 조회
    @GetMapping("/inquiries/{userId}")
    public ResponseEntity<?> getInquries(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(inquiryService.getInquiries(userId), HttpStatus.OK);
    }


    // ex) localhost:8080/inquiry/3
    // 단건 게시글 조회
    @GetMapping("/inquiry/{id}")
    public ResponseEntity<?> getInquiry(@PathVariable("id") Long id) {
        return new ResponseEntity<>(inquiryService.getInquiry(id), HttpStatus.OK);
    }


    // localhost:8080/inquiry/new (Only POST)
    // POST 게시글 작성
    @PostMapping("/inquiry/new")
    public ResponseEntity<?> save(@RequestBody Inquiry inquiry) {
        return new ResponseEntity<>(inquiryService.save(inquiry), HttpStatus.CREATED);
    }


    // PUT 게시글 수정
    // ex) localhost:8080/inquiry/3
    // 게시글 수정하고 -> 완료 버튼을 누른다 -> 백엔드 서버 요청 (id, updateInquiry)
    @PutMapping("/inquiry/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Inquiry updateInquiry) {
        return new ResponseEntity<>(inquiryService.update(id, updateInquiry), HttpStatus.OK);
    }


    // DELETE 게시글 삭제
    // ex) localhost:8080/boards/3
    // 게시글 삭제하기
    @DeleteMapping("/inquiry/{id}")
    public ResponseEntity<?> deleteInquiry(@PathVariable("id") Long id) {
        inquiryService.deleteInquiry(id);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
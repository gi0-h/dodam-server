package com.example.dodam.service;

import com.example.dodam.model.Inquiry;
import com.example.dodam.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }
    @Transactional(readOnly = true)
    public List<Inquiry> getInquiries() {
        List<Inquiry> inquiries = inquiryRepository.findAll();
        return inquiries;
    }

    @Transactional(readOnly = true)
    public Inquiry getInquiry(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id).get();
        return inquiry;
    }

    @Transactional
    public Inquiry save(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }

    @Transactional
    // Transactional 을 붙이면 더티체킹이 일어나서, 저장하지 않아도 메서드가 성공적으로 끝나면 저장이 된다.
    public Inquiry editInquiry(Long id, Inquiry updateInquiry) {
        // 1. 기존 게시물을 꺼내온다
        Inquiry board = inquiryRepository.findById(id).get();

        // 2. 기존 게시물에, updateBoard 정보를 덮어씌워준다.
        board.setTitle(updateInquiry.getTitle());
        board.setContent(updateInquiry.getContent());

        return board;
    }


    @Transactional
    public void deleteInquiry(Long id) {
        inquiryRepository.deleteById(id);
    }

}
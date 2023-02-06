package com.example.dodam.service;

import com.example.dodam.InquiriesDto;
import com.example.dodam.InquiryDto;
import com.example.dodam.model.Inquiry;
import com.example.dodam.repository.InquiryRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


//@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }
    @Transactional(readOnly = true)
    public List<InquiriesDto> getInquiries() {
        List<Inquiry> inquiries = inquiryRepository.findAll();
        List<InquiriesDto> inquiriesDtos = new ArrayList<>();
        inquiries.forEach(s-> inquiriesDtos.add(InquiriesDto.inquiriesDto(s)));
        return inquiriesDtos;
    }

    @Transactional(readOnly = true)
    public InquiryDto getInquiry(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id).get();
        InquiryDto inquiryDto = InquiryDto.inquiryDto(inquiry);
        return inquiryDto;
    }

    @Transactional
    public Inquiry save(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }

    @Transactional
    public Inquiry update(Long id, Inquiry updateInquiry) {
        return inquiryRepository.update(id, updateInquiry);
    }


    @Transactional
    public void deleteInquiry(Long id) {
        inquiryRepository.deleteById(id);
    }

}
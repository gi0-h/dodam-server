package com.example.dodam.repository;

import com.example.dodam.domain.Inquiry;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
public interface InquiryRepository {
    Inquiry save(Inquiry inquiry);
    Optional<Inquiry> findById(Long id);
    Optional<Inquiry> deleteById(Long id);
    List<Inquiry> findAll();

    @Transactional
    Inquiry update(Long id, Inquiry inquiry);
}

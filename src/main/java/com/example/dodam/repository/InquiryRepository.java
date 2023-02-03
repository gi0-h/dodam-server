package com.example.dodam.repository;

import com.example.dodam.model.Inquiry;

import java.util.List;
import java.util.Optional;
public interface InquiryRepository {
    Inquiry save(Inquiry inquiry);
    Optional<Inquiry> findById(Long id);
    Optional<Inquiry> deleteById(Long id);
    List<Inquiry> findAll();

}

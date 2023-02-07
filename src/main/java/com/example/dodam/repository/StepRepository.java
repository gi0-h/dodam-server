package com.example.dodam.repository;

import com.example.dodam.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step,Long> {
    List<Step> findAllByNickName(String nickName);

    Step findByOrderAndNickName(int order, String nickName);

    Step findByStepId(Long id);

    Long countStepByNickName(String nickName);
}

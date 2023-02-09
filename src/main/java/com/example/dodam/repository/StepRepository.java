package com.example.dodam.repository;

import com.example.dodam.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    Step findByStepId(int stepId);

    List<Step> findAllByUserId(int userId);

    Step findByStepOrderAndUserId(int order, int userId);

    Long countStepByUserId(int userId);

    @Modifying(clearAutomatically = true)
    @Query("update Step s set s.stepOrder = s.stepOrder - 1 " +
            "WHERE s.stepOrder > :deleteOrder")
    int updateOrder(@Param("deleteOrder") int deleteOrder);
}

package com.example.dodam.dto;

import com.example.dodam.entity.Step;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public class StepEnrollDto {
    private List<Step> allStep;
    private LocalDate startDate;
}

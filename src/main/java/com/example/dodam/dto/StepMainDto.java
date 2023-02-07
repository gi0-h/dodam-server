package com.example.dodam.dto;

import com.example.dodam.entity.Step;
import lombok.Builder;

import java.util.List;

@Builder
public class StepMainDto {
    private String memberNickName;
    private int dDay;
    private String nowStep;
    private List<Step> allStep;
}

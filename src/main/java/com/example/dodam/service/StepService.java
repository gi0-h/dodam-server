package com.example.dodam.service;

import com.example.dodam.dto.StepAddDto;
import com.example.dodam.dto.StepEnrollDto;
import com.example.dodam.dto.StepMainDto;
import com.example.dodam.dto.StepSelectDto;
import com.example.dodam.domain.Step;
import com.example.dodam.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StepService {

    private final StepRepository stepRepository;
//    private final MemberService memberService;

    public StepMainDto getMainStep(int userId) {
//        String memberNickName = memberservice.getMemberNickName(userId);
//        LocalDate startDate = memberService.getStartDate();
        LocalDate startDate = LocalDate.of(2023,01,01);
//        int dDay = Period.between(startDate,LocalDate.now()).getDays();
        LocalDate now = LocalDate.now();
        int dDay = (int) ChronoUnit.DAYS.between(startDate,now);
        String memberNickName = "temp";

        List<Step> stepAll = stepRepository.findAllByUserId(userId);
        List<String> nowStep = new ArrayList<>();

        for(int i=0; i<stepAll.size(); i++){
            if(stepAll.get(i).getStartDate().compareTo(now) <= 0 && stepAll.get(i).getEndDate().compareTo(now) >= 0){
                nowStep.add(stepAll.get(i).getStepName());
            }
        }

        return StepMainDto.builder()
                .allStep(stepAll)
                .nowStep(nowStep)
                .dDay(dDay)
                .memberNickName(memberNickName).build();

    }

    public StepEnrollDto getStepEnroll(int userId) {
//        LocalDate startDate = memberService.getStartDate();
        LocalDate startDate = LocalDate.now(); //temp
        List<Step> stepAll = stepRepository.findAllByUserId(userId);

        return StepEnrollDto.builder()
                .startDate(startDate)
                .allStep(stepAll)
                .build();
    }

    public void changeOrder(int userId, int firstOrder, int secondOrder) {
        Step step1 = stepRepository.findByStepOrderAndUserId(firstOrder,userId);
        Step step2 = stepRepository.findByStepOrderAndUserId(secondOrder,userId);

        step1.changeOrder(secondOrder);
        step2.changeOrder(firstOrder);
    }

    public Step getStep(int stepId) {
        return stepRepository.findByStepId(stepId);
    }

    public void changeStep(StepSelectDto dto) {
        Step step = stepRepository.findByStepId(dto.getStepId());
        step.changeStep(dto.getStepName(),dto.getStartDate(),dto.getEndDate());
    }

    public void delete(int stepId) {
        Step step = stepRepository.findByStepId(stepId);
        int stepOrder = step.getStepOrder();
        stepRepository.delete(step);
        stepRepository.updateOrder(stepOrder);
    }

    public int addStep(int userId, StepAddDto dto) {
        Long order = stepRepository.countStepByUserId(userId);
        Step step = Step.builder()
                .userId(userId)
                .stepName(dto.getStepName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .stepOrder(Math.toIntExact(order)).build();
        stepRepository.save(step);
        return step.getStepId();
    }
}

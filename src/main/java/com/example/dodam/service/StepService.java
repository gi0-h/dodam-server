package com.example.dodam.service;

import com.example.dodam.dto.StepEnrollDto;
import com.example.dodam.dto.StepMainDto;
import com.example.dodam.dto.StepSelectDto;
import com.example.dodam.entity.Step;
import com.example.dodam.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StepService {

    private final StepRepository stepRepository;
//    private final MemberService memberService;

    public StepMainDto getMainStep(String nickName) {
//        int dDay = memberService.getDDay();
        int dDay = 1; //temp

        List<Step> stepAll = stepRepository.findAllByNickName(nickName);
        LocalDate now = LocalDate.now();
        String nowStep = null;

        for(int i=0; i<stepAll.size(); i++){
            if(stepAll.get(i).getStartDate().compareTo(now) == -1 && stepAll.get(i).getEndDate().compareTo(now) == 1){
                nowStep = stepAll.get(i).getName();
                break;
            }
        }

        return StepMainDto.builder()
                .allStep(stepAll)
                .nowStep(nowStep)
                .dDay(dDay)
                .memberNickName(nickName).build();

    }

    public StepEnrollDto getStepEnroll(String nickName) {
//        LocalDate startDate = memberService.getStartDate();
        LocalDate startDate = LocalDate.now(); //temp
        List<Step> stepAll = stepRepository.findAllByNickName(nickName);

        return StepEnrollDto.builder()
                .startDate(startDate)
                .allStep(stepAll)
                .build();
    }

    public void chageOrder(String nickName, int firstOrder, int secondOrder) {
        Step step1 = stepRepository.findByOrderAndNickName(firstOrder,nickName);
        Step step2 = stepRepository.findByOrderAndNickName(secondOrder,nickName);

        step1.changeOrder(secondOrder);
        step2.changeOrder(firstOrder);
    }

    public Step getStep(Long stepId) {
        return stepRepository.findByStepId(stepId);
    }

    public void changeStep(Long stepId, String stepName, LocalDate startDate, LocalDate endDate) {
        Step step = stepRepository.findByStepId(stepId);
        step.changeStep(stepName,startDate,endDate);
    }

    public void delete(Long stepId) {
        Step step = stepRepository.findByStepId(stepId);
        stepRepository.delete(step);
    }

    public Long addStep(String nickName, StepSelectDto stepSelectDto) {
//        Long userId = memberService.getUserId(nickName);
        Long userId = 1L; //temp
        Long order = stepRepository.countStepByNickName(nickName);
        Step step = Step.builder()
                .userId(userId)
                .name(stepSelectDto.getStepName())
                .startDate(stepSelectDto.getStartDate())
                .endDate(stepSelectDto.getEndDate())
                .order(Math.toIntExact(order)).build();
        stepRepository.save(step);
        return step.getId();
    }
}

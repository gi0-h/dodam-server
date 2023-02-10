package com.example.dodam.service;

import com.example.dodam.dto.StepAddDto;
import com.example.dodam.dto.StepEnrollDto;
import com.example.dodam.dto.StepMainDto;
import com.example.dodam.dto.StepSelectDto;
import com.example.dodam.domain.Step;
import com.example.dodam.repository.StepRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@SpringBootTest
@Transactional
class StepServiceTest {
    @Autowired
    StepService stepService;

    @Autowired
    StepRepository stepRepository;

    public Step createStep(){
        Step step = Step.builder()
                .userId(0)
                .stepName("first")
                .startDate(LocalDate.of(2023,01,01))
                .endDate(LocalDate.now())
                .stepOrder(0).build();
        return step;
    }

    public void createEx(){
        StepAddDto dto = StepAddDto.builder()
                .stepName("first")
                .startDate(LocalDate.of(2023,01,01))
                .endDate(LocalDate.now())
                .build();

        stepService.addStep(0,dto);
        stepService.addStep(0,dto);
        stepService.addStep(1,dto);
        stepService.addStep(0,dto);
        System.out.println("stepRepository.findAllByUserId(0) = " + stepRepository.findAllByUserId(0));
    }

    @Test
    @DisplayName("단계등록 Test")
    public void SaveStepTest(){
        //given
        StepAddDto dto = StepAddDto.builder()
                .stepName("first")
                .startDate(LocalDate.of(2023,01,01))
                .endDate(LocalDate.now())
                .build();
        //when
        stepService.addStep(0,dto);
        stepService.addStep(0,dto);
        stepService.addStep(1,dto);
        stepService.addStep(0,dto);
        //then
        //저장확인
        Assertions.assertThat(stepRepository.findAllByUserId(0).get(0).getUserId()).isEqualTo(0);
        //순서확인
        Assertions.assertThat(stepRepository.findAllByUserId(0).get(2).getStepOrder()).isEqualTo(2);
        System.out.println("stepRepository.findAllByUserId(0) = " + stepRepository.findAllByUserId(0));
        System.out.println("stepRepository = " + stepRepository.findAll());
    }

    @Test
    @DisplayName("단계삭제 Test")
    public void deleteTest(){
        //given
        createEx();

        //when
        stepService.delete(2);

        //then
        System.out.println("stepRepository.findAllByUserId(0) = " + stepRepository.findAllByUserId(0));
        Assertions.assertThat(stepRepository.findAllByUserId(0).get(1).getStepOrder()).isEqualTo(1);

    }

    @Test
    @DisplayName("단계 변경Test")
    public void changeStepTest(){
        //given
        createEx();
        StepSelectDto dto = StepSelectDto.builder()
                .stepId(1)
                .stepName("changeStep")
                .startDate(LocalDate.of(2002,07,15))
                .endDate(LocalDate.now()).build();
        //when
        stepService.changeStep(dto);
        
        //then 
        Step changeStep = stepRepository.findByStepId(1);
        System.out.println("changeStep.toString() = " + changeStep.toString());
        System.out.println("stepRepository = " + stepRepository.findAll());
        Assertions.assertThat(changeStep.getStepName()).isEqualTo("changeStep");
    }

    @Test
    @DisplayName("순서 변경 Test")
    public void changeOrder(){
        //given
        createEx();
        StepSelectDto dto = StepSelectDto.builder()
                .stepId(2)
                .stepName("second")
                .startDate(LocalDate.of(2002,07,15))
                .endDate(LocalDate.now()).build();
        stepService.changeStep(dto);

        //when
        Step stepOrigin0 = stepRepository.findByStepOrderAndUserId(0,0);
        Step stepOrigin1 = stepRepository.findByStepOrderAndUserId(1,0);
        stepService.changeOrder(0,0,1);

        //then
        Assertions.assertThat(stepOrigin0.getStepOrder()).isEqualTo(1);
        Assertions.assertThat(stepOrigin1.getStepOrder()).isEqualTo(0);
        System.out.println("stepRepository = " + stepRepository.findAll());
    }

    @Test
    @DisplayName("단계등록Get Test")
    public void getStepEnrollTest(){
        //given
        createEx();

        //when
        StepEnrollDto stepEnrollDto =  stepService.getStepEnroll(0);

        //then
        System.out.println("stepEnrollDto.toString() = " + stepEnrollDto.toString());
    }

    @Test
    public void getMainStepTest(){
        //given
        createEx();

        //when
        StepMainDto stepMainDto = stepService.getMainStep(0);
        
        //then
        System.out.println("stepRepository.findAll() = " + stepRepository.findAll());
        System.out.println("stepMainDto.toString() = " + stepMainDto.toString());
    }
}
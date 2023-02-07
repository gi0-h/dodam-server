package com.example.dodam.controller;

import com.example.dodam.dto.StepEnrollDto;
import com.example.dodam.dto.StepMainDto;
import com.example.dodam.dto.StepSelectDto;
import com.example.dodam.entity.Step;
import com.example.dodam.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/step")
@RequiredArgsConstructor
public class StepController {

    // private final MemberService memberService;
    private final StepService stepService;

    @GetMapping("/main")
    public StepMainDto main(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String nickName =  authentication.getNickName();
//        or
//        String nickName = memberService.findNickName();
        String nickName = "temp";
        return stepService.getMainStep(nickName);
    }

    @GetMapping("/enroll")
    public StepEnrollDto getStepEnroll() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String nickName =  authentication.getNickName();
//        or
//        String nickName = memberService.findNickName();
        String nickName = "temp";
        return stepService.getStepEnroll(nickName);
    }

    @PutMapping("/enroll")
    public @ResponseBody ResponseEntity changeOrder(int firstOrder, int secondOrder){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String nickName =  authentication.getNickName();
//        or
//        String nickName = memberService.findNickName();
        String nickName = "temp";
        stepService.chageOrder(nickName, firstOrder, secondOrder);

        return new ResponseEntity<String>(nickName, HttpStatus.OK);
    }

    @GetMapping("/select")
    public Step getStep(Long stepId){
        return stepService.getStep(stepId);
    }

    @PutMapping("/select")
    public @ResponseBody ResponseEntity change(Long stepId, String stepName, LocalDate startDate, LocalDate endDate){
        stepService.changeStep(stepId, stepName,startDate, endDate);
        return new ResponseEntity<Long>(stepId, HttpStatus.OK);
    }

    @DeleteMapping("select")
    public @ResponseBody ResponseEntity delete(Long stepId){
        stepService.delete(stepId);
        return new ResponseEntity<Long>(stepId, HttpStatus.OK);
    }

    @PostMapping("/")
    public @ResponseBody ResponseEntity add(StepSelectDto stepSelectDto){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String nickName =  authentication.getNickName();
//        or
//        String nickName = memberService.findNickName();
        String nickName = "temp";
        Long stepId = stepService.addStep(nickName, stepSelectDto);
        return new ResponseEntity<Long>(stepId, HttpStatus.OK);
    }
}

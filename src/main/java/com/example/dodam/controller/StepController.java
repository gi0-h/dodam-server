package com.example.dodam.controller;

import com.example.dodam.config.auth.PrincipalDetails;
import com.example.dodam.domain.user.User;
import com.example.dodam.dto.*;
import com.example.dodam.domain.Step;
import com.example.dodam.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/step")
@RequiredArgsConstructor
public class StepController {

    private final StepService stepService;

    @GetMapping("/main")
    public StepMainDto main(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        return stepService.getMainStep(user);
    }

    @GetMapping("/enroll")
    public StepEnrollDto getStepEnroll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        return stepService.getStepEnroll(user);
    }

    @PutMapping("/enroll")
    public @ResponseBody ResponseEntity changeOrder(@RequestBody StepChangeOrderDto dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Long userId = principalDetails.getUser().getId();
        stepService.changeOrder(userId, dto.getFirstOrder(), dto.getSecondOrder());

        return new ResponseEntity<Long>(userId, HttpStatus.OK);
    }

    @GetMapping("/select")
    public Step getStep(Integer stepId){
        return stepService.getStep(stepId);
    }

    @PutMapping("/select")
    public @ResponseBody ResponseEntity change(@RequestBody StepSelectDto dto){
        stepService.changeStep(dto);
        return new ResponseEntity<Integer>(dto.getStepId(), HttpStatus.OK);
    }

    @DeleteMapping("/select")
    public @ResponseBody ResponseEntity delete(Integer stepId){
        stepService.delete(stepId);
        return new ResponseEntity<Integer>(stepId, HttpStatus.OK);
    }

    @PostMapping("/")
    public @ResponseBody ResponseEntity add(@RequestBody StepAddDto dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Long userId = principalDetails.getUser().getId();
        Integer stepId = stepService.addStep(userId, dto);
        return new ResponseEntity<Integer>(stepId, HttpStatus.OK);
    }
}

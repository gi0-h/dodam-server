package com.example.dodam.controller;

import com.example.dodam.dto.*;
import com.example.dodam.domain.Step;
import com.example.dodam.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/step")
@RequiredArgsConstructor
public class StepController {

    private final StepService stepService;

    @GetMapping("/main/{id}")
    public StepMainDto main(@PathVariable Integer id){
        return stepService.getMainStep(id);
    }

    @GetMapping("/enroll/{id}")
    public StepEnrollDto getStepEnroll(@PathVariable Integer id) {
        return stepService.getStepEnroll(id);
    }

    @PutMapping("/enroll/{id}")
    public @ResponseBody ResponseEntity changeOrder(@PathVariable Integer id, @RequestBody StepChangeOrderDto dto){
        stepService.changeOrder(id, dto.getFirstOrder(), dto.getSecondOrder());

        return new ResponseEntity<Integer>(id, HttpStatus.OK);
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

    @PostMapping("/{id}")
    public @ResponseBody ResponseEntity add(@PathVariable Integer id,@RequestBody StepAddDto dto){
        Integer stepId = stepService.addStep(id, dto);
        return new ResponseEntity<Integer>(stepId, HttpStatus.OK);
    }
}

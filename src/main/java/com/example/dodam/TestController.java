package com.example.dodam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 테스트 파일
 */
@RestController
public class TestController {
    @GetMapping("/test")  //; 없음
    public String test(){
        return "Hello World!";
    }

}

package com.example.dodam.controller;

import com.example.dodam.domain.model.Diary;
import com.example.dodam.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
public class DiaryController {

    private final DiaryService diaryService ;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }



    // 다이어리 목록 조회
    @GetMapping("/diary")
    public List<Diary> getDiary(){
        List<Diary>  diaryList = diaryService.findDiarys();
        return diaryList;
    }

    //다이어리 등록
//    @PostMapping("members/new")
//    public String create(MemberForm form){
//        Member member = new Member();
//        member.setName(form.getName());
//        memberService.join(member);
//        return "redirect:/";
//    }
    @PostMapping("/diary")
    public String addDiary(@RequestBody Diary diary){
        diaryService.addDiary((diary));

        return "성공";
    }

//    @GetMapping("/diary")
//    public Diary getDiary(){
//        Diary diary = new Diary();
//        diary.id = 1;
//        diary.userId = 1;
//        diary.imgPath = "../";
//        diary.date = new Date();
//        diary.feel = "좋음";
//        diary.oneWord = "힘들다..";
//        return diary;
//    }


//    @GetMapping("/info")
//    public String home(){
//        return "hello";
//    }

//
//    @PostMapping("/diary/add")
//    public String
}
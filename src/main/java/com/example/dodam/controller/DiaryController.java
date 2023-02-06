package com.example.dodam.controller;

import com.example.dodam.domain.model.Diary;
import com.example.dodam.domain.model.DiaryDetail;
import com.example.dodam.domain.model.DiaryList;
import com.example.dodam.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
public class DiaryController {

    private final DiaryService diaryService ;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }



    // 다이어리 목록 조회
    @GetMapping("/diary/{id}")
    public List<DiaryList> getDiaryList(@PathVariable Integer id){
        List<DiaryList>  diaryList = diaryService.findDiarys(id);
        return diaryList;
    }

    // 다이어리 디테일 조회
    @GetMapping("/diary/detail/{id}")
    public Optional<DiaryDetail> getDiaryDetail(@PathVariable Integer id){
        Optional<DiaryDetail> diaryList = diaryService.findDiary(id);
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

    @PutMapping("/diary")
    public String putDiary(@RequestBody Diary diary){
        diaryService.updateDiary(diary);
        return "성공";
    }

    @DeleteMapping("/diary/{id}")
    public String deleteDiary(@PathVariable Integer id){
        //해당 id 다이어리 존재 여부 확인
        diaryService.deleteDiary(id);
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
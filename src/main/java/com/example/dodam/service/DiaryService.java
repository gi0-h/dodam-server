package com.example.dodam.service;

import com.example.dodam.domain.model.Diary;
import com.example.dodam.repository.DiaryRepository;


import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DiaryService {

    private final DiaryRepository diaryRepository ;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }


    //다이어리 목록 조회
    public List<Diary> findDiarys(){
        return diaryRepository.findAll();
    }

    //다이어리 등록
    public Integer addDiary(Diary diary){
        //중복 다이어리 확인
        validateDuplicateDiary(diary);
        diaryRepository.save(diary);
        return diary.getId();
    }

    // 다이어리 중복 여부 확인
    private void validateDuplicateDiary(Diary diary) {
        String diaryDate = diary.getDate().toInstant()
                .atOffset(ZoneOffset.UTC)
                .format( DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        diaryRepository.findByDate(diaryDate).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 다이어리");

        });
    }



}

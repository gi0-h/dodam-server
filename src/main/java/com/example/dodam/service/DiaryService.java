package com.example.dodam.service;

import com.example.dodam.domain.model.Diary;
import com.example.dodam.domain.model.DiaryDetail;
import com.example.dodam.domain.model.DiaryList;
import com.example.dodam.repository.DiaryRepository;


import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class DiaryService {

    private final DiaryRepository diaryRepository ;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }


    //다이어리 목록 조회
    public List<DiaryList> findDiarys(Integer id ){
        return diaryRepository.findAll(id);
    }
    //다이어리 조회
    public Optional<DiaryDetail> findDiary(Integer id ){
        return Optional.of(diaryRepository.findDiary(id));
    }

    //다이어리 등록
    public Integer addDiary(Diary diary){
        //중복 다이어리 확인
        validateDuplicateDiary(diary);
        diaryRepository.save(diary);
        return diary.getId();
    }
    // 다이어리 수정
    public Integer updateDiary(Diary diary){
        //존재하는 다이어리인지 확인
        diaryRepository.updateDiary(diary);
        return diary.getId();

    }

    //다이어리 삭제
    public Integer deleteDiary(Integer id){
        diaryRepository.deleteDiary(id);
        return id;
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

package com.example.dodam.service;

import com.example.dodam.domain.diary.Diary;
import com.example.dodam.domain.diary.DiaryDetail;
import com.example.dodam.domain.diary.DiaryList;
import com.example.dodam.repository.DiaryRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
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
    //다이어리 이미지 저장
    public String saveDiaryImag( MultipartFile imgFile) throws Exception{
        String imagePath = null ;
        String absolutePath = new File("").getAbsolutePath()+"\\" ;
        String path = "image/diary";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!imgFile.isEmpty()) {
            String contentType = imgFile.getContentType();
            String originalFileExtension;
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else {
                    throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
                }
            }
//            imagePath = path + "/" + "0" + originalFileExtension;
            imagePath = "/Users/gimga-eun/Desktop/대학/대외활동/도담/Dodam-Server/image/diary/"+ "0" + originalFileExtension;
            file = new File(imagePath);
            imgFile.transferTo(file);
        }
        else {
            throw new Exception("이미지 파일이 비어있습니다.");
        }

        return imagePath;
    }
    // 다이어리 이미지 조회
    public byte[] getImage(String imagePath) throws Exception {
        System.out.println(imagePath);
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        String absolutePath = new File("").getAbsolutePath() + "/";
        try {
            inputStream = new FileInputStream(absolutePath + imagePath);
        }
        catch (FileNotFoundException e) {
            throw new Exception("해당 파일을 찾을 수 없습니다.");
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try {
            while((readCount = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, readCount);
            }
            fileArray = outputStream.toByteArray();
            inputStream.close();
            outputStream.close();

        }
        catch (IOException e) {
            throw new Exception("파일을 변환하는데 문제가 발생했습니다.");
        }

        return fileArray;
    }

}

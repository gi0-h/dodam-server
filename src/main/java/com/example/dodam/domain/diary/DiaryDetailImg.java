package com.example.dodam.domain.diary;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class DiaryDetailImg {
    public Integer id;
    public byte[] img;
    public String title;
    public String oneWord;
    public String content;
}

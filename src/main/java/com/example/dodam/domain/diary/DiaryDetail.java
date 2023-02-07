package com.example.dodam.domain.diary;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiaryDetail {
    public Integer id;
    public String title;
    public String imgPath;
    public String oneWord;
    public String content;


}

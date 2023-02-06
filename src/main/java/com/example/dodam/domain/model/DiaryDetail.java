package com.example.dodam.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiaryDetail {
    public Integer id;
    public Date date;
    public String imgPath;
    public String oneWord;


}

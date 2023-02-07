package com.example.dodam.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class Diaryimg {
    public Integer id;
    public MultipartFile img;


}

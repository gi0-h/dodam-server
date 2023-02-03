package com.example.dodam.model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry {
    private Long id;
    private int userId;
    private String title;
    private String content;
    private String answer;
    private String status;
    private String category;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String imgPath;

}

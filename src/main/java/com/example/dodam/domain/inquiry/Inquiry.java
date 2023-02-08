package com.example.dodam.domain.inquiry;

import java.time.LocalDateTime;

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
    private Long userId;
    private String title;
    private String content;
    private String answer;
    private boolean status; //답변중일시 false, 답변 완료시 true가 되어야 함, status의 자료형은 고민필요
    private String category;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String imgPath;

}

package com.example.dodam.model;

import lombok.*;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(userIdx, nickname)를 받는 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.
/*
 * Req.java: From Client To Server
 * 주문정보 수정 요청(Patch Request)을 하기 위해 서버에 전달할 데이터의 형태
 */
public class PatchOrderReq {  //수정에 사용되는 모든 변수들
    /* 기존 코드
    private int userIdx;
    private String nickname;

     */
    private int userId;   //where 절에 사용하는 변수

    //수정할 변수들
    private  String address;    //주소
    private  String babyName;    //아기이름
    private  String diaryTitle;   //다이어리 제목
    private  String startDate;    //시작일시
    private  String endDate;      //종료일시
    private  int templateNo;      //템플릿번호
    public   String isDeleted;    //삭제여부
    private  String deletedTime;  //삭제일시

}

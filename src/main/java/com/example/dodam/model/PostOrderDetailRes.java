package com.example.dodam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * orderId에 따라 상세주문 생성
 */
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class PostOrderDetailRes {
    private int orderDetailId;   //상세주문아이디(자동생성됨)

}


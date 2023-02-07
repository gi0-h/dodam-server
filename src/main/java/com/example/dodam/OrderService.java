package com.example.dodam;

import com.example.dodam.config.BaseException;
import com.example.dodam.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.dodam.config.BaseResponseStatus.*;

/**
 *  *추가한 파일(원래는 provider와 같은 파일에 있었음 -> 분리시킴)
 *  * 조회 기능을 제회한 나머지 inset, update, delete 구현
 */
@Service
public class OrderService {
    private final OrderProvider orderProvider;
    private OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao, OrderProvider orderProvider) {

        this.orderDao = orderDao;
        this.orderProvider = orderProvider;
    }


    //주문등록
    public PostOrderRes postOrder(PostOrderReq postOrderReq){
        int orderId=orderDao.addOrder(postOrderReq);
        PostOrderRes postOrderRes=new PostOrderRes(orderId);

        return postOrderRes;

    }

    //상세주문등록
    public PostOrderDetailRes postOrderDetail(PostOrderDetailReq postOrderDetailReq, int orderId){
        int orderDetailId=orderDao.addOrderDetail(postOrderDetailReq, orderId);
        PostOrderDetailRes postOrderDetailRes=new PostOrderDetailRes(orderDetailId);

        return postOrderDetailRes;

    }

    //주문정보 변경(Patch)
    public void modifyOrder(PatchOrderReq patchOrderReq) throws BaseException {

        try {
            int result = orderDao.modifyOrder(patchOrderReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }


    //***********************************************************************************************************

    /*
    //주문삭제(DELETE)  (사용x)
    public void deleteOrder(DeleteOrderReq deleteOrderReq) throws BaseException{
        try {
            int result = orderDao.deleteOrder(deleteOrderReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(DELETE_FAIL);    //추가한 에러코드
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

     */

    //***********************************************************************************************************

    //주문삭제
    public void deleteOrder(DeleteOrderReq deleteOrderReq) throws BaseException {
        try {
            int result = orderDao.deleteOrder(deleteOrderReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(DELETE_FAIL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
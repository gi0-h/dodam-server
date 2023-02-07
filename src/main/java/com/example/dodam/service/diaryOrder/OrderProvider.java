package com.example.dodam.service.diaryOrder;

import com.example.dodam.config.BaseException;
import com.example.dodam.model.GetOrderDetailRes;
import com.example.dodam.model.GetOrderRes;
import com.example.dodam.repository.diaryOrder.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.dodam.config.BaseResponseStatus.DATABASE_ERROR;

/**
 * * select(조회) 구현
 */
@Service
public class OrderProvider {
    private  final OrderDao orderDao;

    @Autowired
    public  OrderProvider(OrderDao orderDao){ this.orderDao=orderDao;}

    //전체주문조회
    public List<GetOrderRes> getOrder(){
        List<GetOrderRes> orderRes=orderDao.orderRes();
        return orderRes;
    }

    //상세 전체주문조회
    public List<GetOrderDetailRes> getOrderDetail(){
        List<GetOrderDetailRes> orderDetailRes=orderDao.orderDetailRes();
        return orderDetailRes;
    }

    // 해당 userId를 갖는 Order 정보 조회
    public GetOrderRes getOrder(int userId) throws BaseException {
        try {
            GetOrderRes getOrderRes = orderDao.getOrder(userId);
            //System.out.println("provider 에서 isDeleted 값 :"+getOrderRes.getIsDeleted());
            return getOrderRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 userId를 갖는 Order 상세 정보 조회
    public GetOrderDetailRes getOrderDetail(int orderId) throws BaseException {
        try {
            GetOrderDetailRes getOrderDetailRes = orderDao.getOrderDetail(orderId);
            return getOrderDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

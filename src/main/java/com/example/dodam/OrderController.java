package com.example.dodam;
import com.example.dodam.config.BaseException;
import com.example.dodam.config.BaseResponse;
import com.example.dodam.config.BaseResponseStatus;
import com.example.dodam.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;
import static com.example.dodam.config.BaseResponseStatus.*;

/**
 * 필요한 모든 기능을 보이는 곳
 */

@RestController
public class OrderController {
    private OrderProvider orderProvider;
    private OrderService orderService;  //*추가

    @Autowired
    public OrderController(OrderProvider orderProvider, OrderService orderService) {

        this.orderProvider = orderProvider;  //provider와 연결
        this.orderService=orderService;      //*추가: service와 연결(원래는 provider하나에 service가 함께 있었는데 분리시켰기 때문에 추가)
    }


    //전체주문조회
    @GetMapping("/orders")  //복수형으로 작성
    public List<GetOrderRes> getOrder() {
        List<GetOrderRes> orderRes = orderProvider.getOrder(); //Order 테이블의 모든 데이터를 가져옴
        return orderRes;

    }

    //상세주문 전체조회
    @GetMapping("/order-details")  //하이픈, 복수형으로 작성
    public List<GetOrderDetailRes> getOrderDetail() {
        List<GetOrderDetailRes> orderDetailRes = orderProvider.getOrderDetail(); //Order 테이블의 모든 데이터를 가져옴
        return orderDetailRes;

    }

    //주문등록
    @ResponseBody
    @PostMapping("/orders") //복수형으로 작성
    public PostOrderRes postOrder(@RequestBody PostOrderReq postOrderReq) {
        PostOrderRes postOrderRes = orderService.postOrder(postOrderReq);
        return postOrderRes;

    }

    //상세주문등록   //orderId를 입력하면 해당 주문에 대한 상세주문 테이블이 생성된다.
    @ResponseBody
    @PostMapping("/order-details/{orderId}") //복수형으로 작성       **orderId가 안넘어감......
    public PostOrderDetailRes postOrderDetail(@PathVariable("orderId") int orderId, @RequestBody PostOrderDetailReq postOrderDetailReq) {
        PostOrderDetailRes postOrderDetailRes = orderService.postOrderDetail(postOrderDetailReq, orderId);
        //int order_Id=orderId;
        System.out.println("orderId값:"+orderId);
        return postOrderDetailRes;

    }

    /**
     *
     * 주문변경
     * 이미 삭제된 주문은 에러메시지를 출력한다.
     * 주문이 존재하는 경우에만 주문변경 가능하도록 한다.
     * 변경은 변경하려는 칼럼만 가져와서 다시 원래 JSON 데이터에 집어넣는 방식으로 이루어진다.
     */
    @ResponseBody
    @PatchMapping("/orders/{userId}") //복수형으로 작성
    public BaseResponse<String> modifyOrder(@PathVariable("userId") int userId, @RequestBody Order order) throws BaseException {

        GetOrderRes getOrderRes = orderProvider.getOrder(userId); //***diaryOrder 테이블의 데이터 가져오는 방법***
        //System.out.println("컨트롤러에서 getIsDeleted 값을 출력:"+getOrderRes.getIsDeleted()); //IsDeleted 값이 잘 출력됨('Y' 또는 'N')
        String checkIsDeleted= getOrderRes.getIsDeleted();
        //System.out.println("컨트롤러에서  checkIsDeleted 값을 출력:"+checkIsDeleted);

        if (checkIsDeleted.equals("Y")) {  //삭제된 주문일 경우
            //주문변경X +에러메시지 출력
            System.out.println("삭제된 주문");
            String result = "이미 삭제된 주문입니다.";
            return new BaseResponse<>(result);
        }
        else { //존재하는 주문일 경우='N'
            System.out.println("존재하는 주문");

            try {
                PatchOrderReq patchOrderReq = new PatchOrderReq(userId, order.getAddress(), order.getBabyName(), order.getDiaryTitle(),
                        order.getStartDate(), order.getEndDate(), order.getTemplateNo(), order.getIsDeleted(),order.getDeletedTime());   //**변경할때는 삭제일시 null로 유지**, 변경할 변수들 모두 order.get___()형태로 입력
                orderService.modifyOrder(patchOrderReq);
                String result = "주문정보가 수정되었습니다.";
                return new BaseResponse<>(result);


            } catch (BaseException exception) {  //예외처리
                return new BaseResponse<>((exception.getStatus()));
            }
        } //else 끝
    }



     //주문 1개 조회 API
     @ResponseBody
     @GetMapping("/orders/{userId}") // (GET) localhost:8080/order/:userId
     public BaseResponse<GetOrderRes> getOrder(@PathVariable("userId") int userId) throws BaseException {

         try {
             GetOrderRes getOrderRes = orderProvider.getOrder(userId);
             if(getOrderRes.getIsDeleted().equals("Y")){  //삭제된 주문일 경우
                 //String result2 = "이미 삭제된 주문입니다.";

                 // **postman 화면출력: 위의 result2는 문자열이라서 여기서 날리지 못함. NOT_EXIST_ERROR는 날릴 수 있다.**
                 return new BaseResponse<>((NOT_EXIST_ERROR));  //조회하지 않고 에러메시지만 출력,

             }
             else{ //존재하는 주문일 경우='N'
                 return new BaseResponse<>(getOrderRes);  //조회
             }

         } catch (BaseException exception) {  //예외처리
             return new BaseResponse<>((exception.getStatus()));
         }

    }

    //상세주문 1개 조회
    @ResponseBody
    @GetMapping("/orders-details/{orderId}") // (GET) localhost:8080/order-details/:userId, 복수형으로 작성
    public BaseResponse<GetOrderDetailRes> getOrderDetail(@PathVariable("orderId") int orderId) {
        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 userId값을 받아옴.
        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
        //  .(dot)이 포함된 경우, .을 포함한 그 뒤가 잘려서 들어감
        // Get Users
        try {
            GetOrderDetailRes getOrderDetailRes = orderProvider.getOrderDetail(orderId);
            return new BaseResponse<>(getOrderDetailRes);
        } catch (BaseException exception) {  //예외처리
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    //***********************************************************************************************************

    /*
     * 이 함수는 테이블에 있는 해당 행을 모두 데이터베이스에서 삭제하는 코드이다.(사용하지 않는다.)
     * 주문 1개 삭제 API
     *
    @ResponseBody
    @DeleteMapping("/orders/{userId}") // (DELETE이므로 DELETE로 매핑) localhost:8080/order/:userId
    public BaseResponse<String> deleteOrder(@PathVariable("userId") int userId) {
        try {

            //userId가 0보다 작을 때
            if(userId <=0){
                throw new BaseException(USERS_EMPTY_USER_ID); //유저아이디 에러 메시지 설정
            }

            DeleteOrderReq deleteOrderReq=new DeleteOrderReq(userId);  //req에는 userId만
            //DeleteOrderRes deleteOrderRes=OrderService.deleteOrder(deleteOrderReq);

            orderService.deleteOrder(deleteOrderReq);
            String result = "주문정보가 삭제되었습니다.";

            return new BaseResponse<>(result);


        } catch (BaseException exception) {  //예외처리
            return new BaseResponse<>((exception.getStatus()));
        }
    }
 */

    //***********************************************************************************************************

    /**
     * 주문삭제
     * 이 함수는 테이블에 있는 해당 행을 모두 데이터베이스에서 삭제하지 않고
     *  isDeleted 칼럼만을 [yer] or [no] 로 업데이트해두는 코드이다.
     * 주문 1개 삭제 API(isDeleted 칼럼 값을 'N'에서 'Y'로 변경한다.
     *
     */
    @ResponseBody
    @PatchMapping("/orders/delete/{userId}") //복수형으로 작성, 삭제이지만 칼럼을 수정하기 때문에 patch를 사용한다.
    public BaseResponse<String> deleteOrder(@PathVariable("userId") int userId, @RequestBody Order order) {
        try {
            DeleteOrderReq deleteOrderReq = new DeleteOrderReq(userId, order.getDeletedTime());   //변경할 변수들 모두 order.get___()형태로 입력
            orderService.deleteOrder(deleteOrderReq);

            String result = "주문정보가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {  //예외처리
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}


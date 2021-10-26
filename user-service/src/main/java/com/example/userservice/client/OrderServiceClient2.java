//package com.example.userservice.client;
//
//import com.example.userservice.vo.ResponseOrder;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
//@FeignClient(name = "order-service") //호출하고자 하는 마이크로서비스의 이름을 넣으시며 됩니다.
//public interface OrderServiceClient2 { //OrderServiceClient2의 역할은 유레카에 등록된 마이크로서비스 중 order-service로 되어진 이름의
//    //마이크로 서비스를 검색하게 될거고
//
//    //서비스가 가진 다양한 uri 에 대해서 이 uri를 호출하겠다.
//    @GetMapping("order-service/{userId}/orders")
//    List<ResponseOrder> getOrders(@PathVariable String userId);
//
//}

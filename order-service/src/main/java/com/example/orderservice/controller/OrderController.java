package com.example.orderservice.controller;

import com.example.orderservice.client.CatalogServiceClient;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.mq.KafkaProducer;
import com.example.orderservice.mq.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.RequestUpdateOrder;
import com.example.orderservice.vo.ResponseCatalog;
import com.example.orderservice.vo.ResponseOrder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/")
@Slf4j
public class OrderController {
    Environment env;
    OrderService orderService;
    KafkaProducer kafkaProducer;
    OrderProducer orderProducer;

    CatalogServiceClient catalogServiceClient;

    @Autowired
    public OrderController(Environment env, OrderService orderService,
                           KafkaProducer kafkaProducer,
                           CatalogServiceClient catalogServiceClient,
                           OrderProducer orderProducer) {
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
        this.catalogServiceClient = catalogServiceClient;
        this.orderProducer = orderProducer;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder orderDetails) {
        log.info("Before add orders data");

        // check how much stock is left
        // order-service -> catalog-service
        // resttemplate or openfeign(o)
        boolean isAvailable = true;
        ResponseCatalog responseCatalog = catalogServiceClient.getCatalog(orderDetails.getProductId());
        if (responseCatalog != null &&
                responseCatalog.getStock() - orderDetails.getQty() < 0)
            isAvailable = false;

        if (isAvailable) {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
            orderDto.setUserId(userId);

            OrderDto createdOrder = orderService.createOrder(orderDto); //이걸쓴건지
//            ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

            /* send message to Kafka topic */
//            createdOrder.setOrderId(UUID.randomUUID().toString());
//            createdOrder.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());
            kafkaProducer.send("example-catalog-topic", createdOrder);
            ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

//            orderProducer.send("orders", orderDto); 이걸쓴거지

            log.info("After added orders data");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
        } else {
            log.info("After added orders data");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) throws Exception {
        log.info("Before retrieve orders data");
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

//        Random rnd = new Random(System.currentTimeMillis());
//        int time = rnd.nextInt(3);
//        if (time % 2 == 0) {
//            try {
//                Thread.sleep(1000);
//                throw new Exception();
//            } catch (InterruptedException ex) {
//                log.warn(ex.getMessage());
//            }
//        }

        log.info("After retrieve orders data");

        return ResponseEntity.status(HttpStatus.OK).body(result);
//        throw new Exception("Server not working!");
    }


    @GetMapping("/orders")
    public ResponseEntity<List<ResponseOrder>> getOrderList(){
        log.info("Before retrieve ALL orders data");

        Iterable<OrderEntity> orderList = orderService.getAllOrders();

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });


        log.info("After retrieve ALL orders data");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{orderId}/orders")
    public void updateCart(@PathVariable("orderId") String orderId , @RequestBody RequestUpdateOrder order){
        log.info("orders statusCode"+ order.getStatusCode());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderDto orderDetails = mapper.map(order, OrderDto.class);

        OrderDto orderDto = orderService.getOrderByOrderId(orderId);

        orderService.updateByOrderId(orderDto, orderDetails);

    }


}
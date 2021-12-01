package com.example.cartservice.controller;

import com.example.cartservice.client.CartServiceClient;
import com.example.cartservice.dto.CartDto;
import com.example.cartservice.entity.CartEntity;
import com.example.cartservice.service.CartService;
import com.example.cartservice.vo.RequestCart;
import com.example.cartservice.vo.ResponseCart;
import com.example.cartservice.vo.ResponseCartShow;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class CartController {
    private final Environment env;
    private final CartService cartService;

   // CartServiceClient cartServiceClient;

    @Autowired
    public CartController(Environment env, CartService cartService ) {
        this.env = env;
        this.cartService = cartService;
    }

    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return String.format("잘 작동됩니다.");
    }

    @Operation(summary = "전체카트조회", description = "hello api example")
    @GetMapping("/carts")
    public ResponseEntity<List<ResponseCart>> getCarts() {
        Iterable<CartEntity> cartList = cartService.getAllCarts();
        List<ResponseCart> result = new ArrayList<>();
        cartList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCart.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/carts")
    public ResponseEntity<List<ResponseCart>> addCarts() {
        Iterable<CartEntity> cartList = cartService.getAllCarts();
        List<ResponseCart> result = new ArrayList<>();
        cartList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCart.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @GetMapping("/{userId}/carts")
    public ResponseEntity<List<ResponseCart>> getOrder(@PathVariable("userId") String userId) throws Exception {
        log.info("Before retrieve orders data");
        Iterable<CartEntity> orderList = cartService.getCartsByUserId(userId);

        List<ResponseCart> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCart.class));
        });


        log.info("After retrieve orders data");

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @PostMapping("/{userId}/carts")
    public ResponseEntity<ResponseCartShow> createOrder(@PathVariable("userId") String userId,
                                                        @RequestBody RequestCart cartDetails) {
        log.info("Before add orders data");

            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            CartDto cartDto = mapper.map(cartDetails, CartDto.class);
            cartDto.setUserId(userId);

            cartDto=cartService.createCart(cartDto); //이걸쓴건지

           ResponseCartShow responseCart = mapper.map(cartDto, ResponseCartShow.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseCart);

    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable("id") String id){
        String msg = "삭제 완료!";
        long id2 = Long.parseLong(id);
//        Integer id2 = Integer.parseInt(id);
        cartService.deleteById(id2);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

//    /*장바구니 수정*/
//    @PutMapping("/{userId}/carts}")
//    public void updateCart(@PathVariable("userId") String userId , @RequestBody RequestCart cart){
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        CartDto cartDetails = mapper.map(cart, CartDto.class);
//
//        CartDto cartDto = cartService.getCartByUserId(userId);
//
//        cartService.updateByProductId(cartDto, cartDetails);
//
//    }



}
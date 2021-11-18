package com.example.cartservice.client;

import com.example.cartservice.vo.ResponseCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="cart-service")
public interface CartServiceClient {

    @GetMapping("/catalogs/{productId}")
    ResponseCart getCart(@PathVariable String productId);

}


//aws 에러뜨면 여기 잘못
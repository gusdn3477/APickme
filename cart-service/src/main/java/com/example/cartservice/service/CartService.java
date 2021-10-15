package com.example.cartservice.service;

import com.example.cartservice.dto.CartDto;
import com.example.cartservice.entity.CartEntity;
import org.springframework.stereotype.Service;


public interface CartService {
    Iterable<CartEntity> getAllCarts();

    Iterable<CartEntity> getCartsByUserId(String userId);

    CartDto createCart(CartDto cartDto);

    void deleteById(long id);
//    void deleteByProductIdAndUserId(String productId, String userId);

//    CartDto getCartByUserId(String userId);
//
//    void updateByProductId(CartDto cartDto, CartDto cartDetails);
    //Iterable<CartEntity> getCartByUserId(String userId);
}

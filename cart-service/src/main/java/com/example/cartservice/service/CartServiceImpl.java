package com.example.cartservice.service;

import com.example.cartservice.dto.CartDto;
import com.example.cartservice.entity.CartEntity;
import com.example.cartservice.jpa.CartRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Data
@Service
@Slf4j
public class CartServiceImpl implements CartService{
    CartRepository cartRepository;
    Environment env;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, Environment env){
        this.cartRepository = cartRepository;
        this.env = env;
    }

    @Override
    public Iterable<CartEntity> getAllCarts(){
        return cartRepository.findAll();
    }

    /*회원 ID로 카트목록들 가져오기*/
    @Override
    public Iterable<CartEntity> getCartsByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public CartDto createCart(CartDto cartDto) {
        cartDto.setOrderId(UUID.randomUUID().toString());
        cartDto.setTotalPrice(cartDto.getUnitPrice()*cartDto.getQty());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CartEntity cartEntity = mapper.map(cartDto, CartEntity.class);

        cartRepository.save(cartEntity);

        return cartDto;
    }

    @Override
    public void deleteById(long id){
        cartRepository.deleteById(id);
    }

//    /* 카트 수정 관련*/
////    @Override
//    public CartDto getCartByUserId(String userId) {
//        CartEntity cartEntity = cartRepository.findByCartId(userId);
//
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        CartDto cartDto = mapper.map(cartEntity, CartDto.class);
//
//        return cartDto;
//    }
//
//    /* 카트 수정 관련*/
//    @Override
//    public CartDto updateByProductId(CartDto cartDto, CartDto cartDetails) {
////        CartEntity cartEntity = cartRepository.findByUserId(cartDto.getUserId());
////        ModelMapper mapper = new ModelMapper();
////        cartDto catalogUpdateDto = mapper.map(cartEntity, CartDto.class);
////
////        cartDto.set(catalogDetails.getProductName());
////        cartDto.setWriter(catalogDetails.getWriter());
////        cartDto.setStock(catalogDetails.getStock());
////        cartDto.setUnitPrice(catalogDetails.getUnitPrice());
////        cartDto.setImage(catalogDetails.getImage());
////        cartDto.setProductId(catalogDto.getProductId());
////
////        ModelMapper catalogmapper = new ModelMapper();
////        catalogmapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
////        CatalogEntity catalogUpdateEntity = catalogmapper.map(catalogUpdateDto, CatalogEntity.class);
////
////        catalogUpdateEntity.setId(userEntity.getId());
////        catalogRepository.save(catalogUpdateEntity);
//
//        return null;
//    }


//    @Override
//    public Iterable<CartEntity> getCartByUserId(String userId){
//        return repository.findByUserId(userId);
//    }
}


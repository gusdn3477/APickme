package com.example.cartservice.jpa;

import com.example.cartservice.entity.CartEntity;
import io.swagger.models.auth.In;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CartRepository extends CrudRepository<CartEntity, Long>{
    Iterable<CartEntity> findByUserId(String userId);

    @Transactional
    void deleteByUserId(String userId);

    @Transactional
    void deleteById(Integer id);

    @Transactional
    void deleteByProductIdAndUserId(String productId, String userId);

    //CartEntity findByuserId(String userId);
    //Iterable<CartEntity> findByUserId(String userId);
}


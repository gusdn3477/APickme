package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createAt;

    private String orderId;
//
//    public void setTotalPrice(Integer totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public Integer getTotalPrice() {
//        return totalPrice * 2;
//    }
}
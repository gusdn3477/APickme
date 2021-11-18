package com.example.cartservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCartShow {
    private String productId;
    //private String userId;
    private String productName;
//    private Integer stock;
    private Integer unitPrice;
    private Integer totalPrice;
    private String imageUrl;
   // private Date CreatedAt;
}

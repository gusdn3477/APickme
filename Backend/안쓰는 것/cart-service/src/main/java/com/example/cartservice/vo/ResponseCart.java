package com.example.cartservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCart {
    private Long id;
    private String productId;
    private String userId;
    private String productName;
    private Integer qty;
    private Integer stock;
    private Integer unitPrice;
    private String imageUrl;
    private Date CreatedAt;
}

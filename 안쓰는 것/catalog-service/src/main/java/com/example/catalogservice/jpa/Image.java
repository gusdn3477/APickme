package com.example.catalogservice.jpa;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String name;
    @Column(length = 30)
    private String productName;
    @Column(length = 10)
    private Integer stock;
    @Column(length = 10)
    private Integer unitPrice;
    @Column(length = 120)
    private String image;
    @Column(length = 50)
    private String writer;
}

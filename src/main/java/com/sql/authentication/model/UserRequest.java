package com.sql.authentication.model;

import jakarta.persistence.*;

import java.util.List;

public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private String allShop;
    @OneToMany
    @JoinColumn(name = "shop_shopCode")
    private Shop shopId;
    @OneToOne
    @JoinColumn(name = "ecategory_id")
    private Ecategory ecategory;
    private List<String> images;
    private String brand;
    private String model;
    private Long minAmount;
    private Long maxAmount;

}

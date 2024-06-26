package com.sql.authentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.results.graph.Fetch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(name = "user_request_shops",
    //         joinColumns = @JoinColumn(name = "userRequest_id"),
    //         inverseJoinColumns = @JoinColumn(name = "shop_registration_id"))
    // private List<ShopRegistration> shop;
    @ManyToOne
    @JoinColumn(name = "ecategory_id")
    private Ecategory ecategory;
    private List<String> images;
    private String brand;
    private String model;
    private String postCondition;
    private Long minAmount;
    private Long maxAmount;
    private String status;
    private String deliverymanName;
    private Long approvedBy;
    private String reason;
    private int requestedShopId;

}

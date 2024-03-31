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
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private String allShop;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_request_shops",
            joinColumns = @JoinColumn(name = "userRequest_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id"))
    private List<Shop> shop;
    @OneToOne
    @JoinColumn(name = "ecategory_id")
    private Ecategory ecategory;
    private List<String> images;
    private String brand;
    private String model;
    private Long minAmount;
    private Long maxAmount;

}

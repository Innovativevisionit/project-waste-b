package com.sql.authentication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class ShopRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private String ShopName;
    private long contactNo;
    private String location;
    private List<String> proofFile;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shop_category",
            joinColumns = @JoinColumn(name = "shopRegistration_id"),
            inverseJoinColumns = @JoinColumn(name = "ecategory_id"))
    private Set<Ecategory> ecategories = new HashSet<>();
    private String recyclingMethod;
    private String handlingHazard;
    private String website;
    private String socialLink;
    private String status;
    private String reason;

}

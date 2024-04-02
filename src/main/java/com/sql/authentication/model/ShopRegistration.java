package com.sql.authentication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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
    @ManyToOne
    @JoinColumn(name = "ecategory_id")
    private Ecategory ecategory;
    private String recyclingMethod;
    private String handlingHazard;
    private String website;
    private String socialLink;
    private String status;
    private String reason;
    private LocalDate approvedDate;

}

package com.sql.authentication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "shops")
@Data
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shopCode;
    @OneToOne
    @JoinColumn(name = "shopRegistration_id")
    private ShopRegistration registration;
    private LocalDate approvedDate;
}

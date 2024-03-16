package com.sql.authentication.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ecategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;
    private int status=1;
}

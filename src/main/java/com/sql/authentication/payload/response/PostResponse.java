package com.sql.authentication.payload.response;

import com.sql.authentication.model.Ecategory;
import com.sql.authentication.model.Shop;
import com.sql.authentication.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class PostResponse {
    private  int id;
    private String name;
    private String userIdEmail;
    private String userIdUsername;
    private String ecategoryName;
    private String brand;
    private String model;
    private List<String> images;
    private String postCondition;
    private Long minAmount;
    private Long maxAmount;
    private String postStatus;
    private String status;

}

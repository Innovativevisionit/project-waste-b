package com.sql.authentication.payload.response;

import com.sql.authentication.model.Ecategory;
import com.sql.authentication.model.ShopRegistration;
import com.sql.authentication.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ShopResponse {
    private Long id;
    private String shopCode;
//    private String registration;
    private LocalDate approvedDate;
    private String userIdUsername;
    private String shopName;
    private long contactNo;
    private String location;
    private String ecategoryName;

}

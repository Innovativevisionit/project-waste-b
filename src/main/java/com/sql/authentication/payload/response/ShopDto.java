package com.sql.authentication.payload.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ShopDto {
    
    private int id;
    private String userIdUsername;
    private String userIdEmail;
    private String ShopName;
    private long contactNo;
    private String location;
    private List<String> images;
    private String ecategoryName;
    private String recyclingMethod;
    private String handlingHazard;
    private String website;
    private String socialLink;
    private String status;
    private String reason;
    private LocalDate approvedDate;

    
}

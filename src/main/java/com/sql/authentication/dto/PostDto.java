package com.sql.authentication.dto;

import lombok.Data;

@Data
public class PostDto {
    
    private Integer postId;
    private String email;
    private String status;
    private String reason;
    private String deliveryMan;
}

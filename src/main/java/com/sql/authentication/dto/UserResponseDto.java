package com.sql.authentication.dto;

import java.util.Set;

import com.sql.authentication.model.Role;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;

    private String location;
    private String mobileNo;
    private String age;
    // private Set<Role> roles;
    private String rolesName;
    private Long aprovedPostCount;
    private Long pendingPostCount;
    
}

package com.sql.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class ShopRegisterDto {
    @NotBlank(message = "Shop Name is Required")
    private String shopName;
    @NotBlank(message = "Contact Number is required")
    @Pattern(regexp = "\\d{10}", message = "Contact should be 10 digits")
    private String contactNo;
    @NotBlank(message = "Location is Required")
    private String location;
//    @NotEmpty(message = "Location is Required")
//    private Set<String> category;
    @NotBlank(message = "Location is Required")
    private String category;
    private List<MultipartFile> files;
    private Integer userId;
    @NotBlank(message = "Recycling Method is required")
    private String recyclingMethod;
    @NotBlank(message = "Handling Hazard is required")
    @Pattern(regexp = "Yes|No", message = "Invalid type")
    private String handlingHazard;
    private String website;
    private String socialLink;
    private LocalDate approvedDate;
}

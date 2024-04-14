package com.sql.authentication.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UserRequestDto {

    private String name;
    @NotBlank(message = "Ecategory is Required")
    private String categories;
    private List<MultipartFile> images;
    private String brand;
    private String model;
    private String postCondition;
    @Pattern(regexp = "[0-9]+", message = "Must contain only numbers")
    private String maxAmount;
    @Pattern(regexp = "[0-9]+", message = "Must contain only numbers")
    private String minAmount;
    private String email;

//    @AssertTrue
//    private boolean isShopId(){
//        if(allShop != null && allShop.equalsIgnoreCase("No")){
//            return !shopId.isEmpty();
//        }
//        return true;
//    }
}

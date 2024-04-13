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
//    @NotBlank(message = "Required Field")
//    @Pattern(regexp = "Yes|No",message = "The Value must be either Yes or No")
    private String allShop;
    private List<String> shopId;
    @NotBlank(message = "Ecategory is Required")
    private String categories;
    private List<MultipartFile> images;
    private String brand;
    private String model;
    private String condition;
    @Pattern(regexp = "[0-9]+", message = "Must contain only numbers")
    private String maxAmount;
    @Pattern(regexp = "[0-9]+", message = "Must contain only numbers")
    private String minAmount;

//    @AssertTrue
//    private boolean isShopId(){
//        if(allShop != null && allShop.equalsIgnoreCase("No")){
//            return !shopId.isEmpty();
//        }
//        return true;
//    }
}

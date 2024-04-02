package com.sql.authentication.controller.ShopRegister;

import com.sql.authentication.dto.ShopRegisterDto;
import com.sql.authentication.dto.ShopUpdateDto;
import com.sql.authentication.model.ShopRegistration;
import com.sql.authentication.payload.response.Response;
import com.sql.authentication.service.Shop.ShopService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/api/shop")
public class ShopController {
    @Autowired private ShopService shopService;
    @PostMapping("/register")
    public Object registerShop(@Valid @ModelAttribute ShopRegisterDto registration, HttpSession session){
        try{
            return shopService.shopRegistration(registration,session);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("requestList")
    public Object request(@RequestParam("status") String status){
        return shopService.registerRequest(status);
    }
    @PutMapping("update")
    public Object update(@Valid  @RequestBody ShopUpdateDto updateDto){
        try {
            ShopRegistration registration=shopService.updateShopRequest(updateDto);
            return ResponseEntity.ok().body(Response.success("Updated Successfully",registration));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/shopList")
    public Object getList(HttpSession session){
        return shopService.shopList(session);
    }
    public UserDetailsImpl getUserDetails(HttpSession session) {
        UserDetailsImpl userDetails = (UserDetailsImpl) session.getAttribute("user");

        System.out.println(userDetails + "Hellooo");
        if (userDetails != null) {
            return userDetails;
        } else {
            throw new RuntimeException("User not found in session");
        }
    }
}

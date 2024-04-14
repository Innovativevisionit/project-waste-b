package com.sql.authentication.controller.ShopRegister;

import com.sql.authentication.dto.ShopRegisterDto;
import com.sql.authentication.dto.ShopUpdateDto;
import com.sql.authentication.model.ShopRegistration;
import com.sql.authentication.payload.response.Response;
import com.sql.authentication.service.Shop.ShopService;
import com.sql.authentication.serviceimplementation.auth.UserDetailsImpl;

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

    //save shop details
    @PostMapping("/register")
    public Object registerShop(
            @RequestPart("email") String email,
            @RequestPart("shopName") String shopName,
            @RequestPart("contactNo") String contactNo,
            @RequestPart("location") String location,
            @RequestPart("category") String category,
            @RequestPart("recycleMethods") String recycleMethods,
            @RequestPart("hazard") String hazard,
            @RequestPart("website") String website,
            @RequestPart("socialLink") String socialLink,
            @RequestPart("images") MultipartFile[] images) {
        try {
            ShopRegisterDto shopDto = new ShopRegisterDto();
            shopDto.setEmail(email);
            shopDto.setShopName(shopName);
            shopDto.setCategory(category);
            shopDto.setLocation(location);
            shopDto.setHandlingHazard(hazard);
            shopDto.setRecyclingMethod(recycleMethods);
            shopDto.setContactNo(contactNo);
            shopDto.setWebsite(website);
            shopDto.setSocialLink(socialLink);
            shopDto.setFiles(Arrays.asList(images));

            return shopService.shopRegistration(shopDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error :"+e.getMessage());
        }
    }

    //pending shop list
    @GetMapping("pendingShop")
    public Object pendingShop(@RequestParam("status") String status){
        return shopService.pendingShop(status);
    }

    //approved shop list
    @GetMapping("approvedShop")
    public Object approvedShop(@RequestParam("status") String status){
        return shopService.approvedShop(status);
    }

    //approve or reject the shop owner
    @PutMapping("update")
    public Object update(@Valid @RequestBody ShopUpdateDto updateDto){
        try {
            ShopRegistration registration=shopService.updateShopRequest(updateDto);
            return ResponseEntity.ok().body(Response.success("Updated Successfully",registration));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//above code completed by divya
    
    @GetMapping("requestList")
    public Object request(@RequestParam("status") String status){
        return shopService.registerRequest(status);
    }

    @GetMapping("/shopList")
    public Object getList(HttpSession session){
        return shopService.shopList(session);
    }
}

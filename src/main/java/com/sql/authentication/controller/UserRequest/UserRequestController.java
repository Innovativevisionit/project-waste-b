package com.sql.authentication.controller.UserRequest;

import com.sql.authentication.dto.UserRequestDto;
import com.sql.authentication.model.UserRequest;
import com.sql.authentication.repository.UserRequestRepository;
import com.sql.authentication.service.UserRequest.UserRequestService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/post")
public class UserRequestController {
    @Autowired
    private UserRequestService requestService;

    @Autowired
    private UserRequestRepository userRequest;
//    @PostMapping("store")
//    public Object store(@RequestBody UserRequestDto userRequestDto, HttpSession session){
//        System.out.println(userRequestDto);
//        return requestService.post(userRequestDto,session);
//    }
@PostMapping("store")
public Object store(@RequestPart("shop_name") String shopName,
                    @RequestPart("brand") String brand,
                    @RequestPart("model") String model,
                    @RequestPart("condition") String condition,
                    @RequestPart("min_amount") String minAmount,
                    @RequestPart("max_amount") String maxAmount,
                    @RequestPart("categories") String categories,
                    @RequestPart MultipartFile[] images,
                    HttpSession session) {
    System.out.println("Hello   " + images);
    // Here, you can create your UserRequestDto object using the received parameters
    UserRequestDto userRequestDto = new UserRequestDto();
//    userRequestDto.setAllShop("Yes");
    userRequestDto.setAllShop(shopName);
    userRequestDto.setShopId(Collections.singletonList(shopName));
    userRequestDto.setBrand(brand);
    userRequestDto.setModel(model);
    userRequestDto.setCondition(condition);
    userRequestDto.setMinAmount(minAmount);
    userRequestDto.setMaxAmount(maxAmount);
    userRequestDto.setCategories(categories);
    // Assuming your DTO has a field to hold images
    userRequestDto.setImages(List.of(images));

    // Now you can call your service method to handle the request
    return requestService.post(userRequestDto, session);
}

    @GetMapping("userList")
    public Object list(HttpSession session){
        return requestService.userPost(session);
    }

    @GetMapping("postList")
    public Object postList(){
        return userRequest.findAll();
    }

}

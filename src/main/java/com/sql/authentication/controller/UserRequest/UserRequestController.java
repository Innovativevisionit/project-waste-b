package com.sql.authentication.controller.UserRequest;

import com.sql.authentication.dto.PostDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/post")
public class UserRequestController {
    @Autowired
    private UserRequestService requestService;

    @Autowired
    private UserRequestRepository userRequest;

    @PostMapping("store")
    public Object store(@RequestPart("name") String name,
                        @RequestPart("categories") String categories,
                        @RequestPart("brand") String brand,
                        @RequestPart("model") String model,
                        @RequestPart("postCondition") String postCondition,
                        @RequestPart("minAmount") String minAmount,
                        @RequestPart("maxAmount") String maxAmount,
                        @RequestPart("email") String email,
                        @RequestPart MultipartFile[] images
                        ) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName(name);
        userRequestDto.setBrand(brand);
        userRequestDto.setModel(model);
        userRequestDto.setPostCondition(postCondition);
        userRequestDto.setMinAmount(minAmount);
        userRequestDto.setMaxAmount(maxAmount);
        userRequestDto.setCategories(categories);
        userRequestDto.setEmail(email);
        userRequestDto.setImages(List.of(images));

        return requestService.post(userRequestDto);
    }

    @GetMapping("userPostList")
    public Object list(@RequestPart("email") String email){
        return requestService.userPost(email);
    }

    @GetMapping("allPostList")
    public Object allPostList(){
        return requestService.allPostList();
    }

    @GetMapping("/get")
    public Object getById(@RequestParam("postId") Integer postId) {
        return requestService.getById(postId);
    }
    
    @GetMapping("/getCategory-based-post")
    public Object getCategoryBasedpost(@RequestParam String email) {
        return requestService.getCategoryBasedpost(email);
    }

    @PostMapping("/post-accept")
    public String acceptPost(@RequestBody PostDto postDto) {
        
        return requestService.acceptPost(postDto);
    }
    
    @GetMapping("user-pending-post")
    public List<String> getPendingPostByUser(@RequestParam String email) {

        return requestService.getPendingPostByUser(email);
    }
    
    @GetMapping("requested-post")
    public Object getRequestedPost(@RequestParam String email) {
        return requestService.getRequestedPost(email);
    }
    
}




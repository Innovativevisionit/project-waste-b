package com.sql.authentication.controller.UserRequest;

import com.sql.authentication.dto.UserRequestDto;
import com.sql.authentication.service.UserRequest.UserRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/post")
public class UserRequestController {
    @Autowired
    private UserRequestService requestService;
    @PostMapping("store")
    public Object store(@Valid @ModelAttribute UserRequestDto userRequestDto){
        return requestService.post(userRequestDto);
    }

}

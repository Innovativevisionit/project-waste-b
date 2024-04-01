package com.sql.authentication.controller.UserRequest;

import com.sql.authentication.dto.UserRequestDto;
import com.sql.authentication.service.UserRequest.UserRequestService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/post")
public class UserRequestController {
    @Autowired
    private UserRequestService requestService;
    @PostMapping("store")
    public Object store(@ModelAttribute UserRequestDto userRequestDto, HttpSession session){
        return requestService.post(userRequestDto,session);
    }

}

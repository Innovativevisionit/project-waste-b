package com.sql.authentication.serviceimplementation.UserRequest;

import com.sql.authentication.dto.UserRequestDto;
import com.sql.authentication.model.UserRequest;
import com.sql.authentication.service.UserRequest.UserRequestService;
import org.springframework.stereotype.Service;

@Service
public class UserRequestServiceImpl implements UserRequestService {
    @Override
    public Object post(UserRequestDto userRequestDto){
        UserRequest userRequest=new UserRequest();
        return userRequestDto;
    }
}

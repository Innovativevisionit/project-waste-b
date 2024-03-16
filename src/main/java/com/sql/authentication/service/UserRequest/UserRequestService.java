package com.sql.authentication.service.UserRequest;

import com.sql.authentication.dto.UserRequestDto;

public interface UserRequestService {
    Object post(UserRequestDto userRequestDto);
}

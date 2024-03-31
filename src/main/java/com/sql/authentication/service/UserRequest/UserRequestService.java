package com.sql.authentication.service.UserRequest;

import com.sql.authentication.dto.UserRequestDto;
import com.sql.authentication.model.UserRequest;
import com.sql.authentication.repository.UserRequestRepository;
import jakarta.servlet.http.HttpSession;

public interface UserRequestService {
    UserRequest post(UserRequestDto userRequestDto, HttpSession session);
}

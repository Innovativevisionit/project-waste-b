package com.sql.authentication.service.UserRequest;

import com.sql.authentication.dto.UserRequestDto;
import com.sql.authentication.model.UserRequest;
import com.sql.authentication.payload.response.PostResponse;
import com.sql.authentication.repository.UserRequestRepository;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserRequestService {
    UserRequest post(UserRequestDto userRequestDto, HttpSession session);
    List<PostResponse> userPost(HttpSession session);
}

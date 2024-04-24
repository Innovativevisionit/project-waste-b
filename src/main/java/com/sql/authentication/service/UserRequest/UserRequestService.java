package com.sql.authentication.service.UserRequest;

import com.sql.authentication.dto.PostDto;
import com.sql.authentication.dto.UserRequestDto;
import com.sql.authentication.model.UserRequest;
import com.sql.authentication.payload.response.PostResponse;
import com.sql.authentication.repository.UserRequestRepository;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

public interface UserRequestService {
    UserRequest post(UserRequestDto userRequestDto);
    List<PostResponse> userPost(String email);
    List<PostResponse> allPostList();
    PostResponse getById(Integer id);
    List<PostResponse> getCategoryBasedpost(String email);
    UserRequest acceptPost(PostDto postDto);
    
    List<String> getPendingPostByUser(String email);

    List<PostResponse> getRequestedPost(String email);

    Map<String,Long> getDashboardCount();
}

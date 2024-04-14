package com.sql.authentication.serviceimplementation.UserRequest;

import com.sql.authentication.Enum.StatusEnum;
import com.sql.authentication.dto.UserRequestDto;
import com.sql.authentication.model.*;
import com.sql.authentication.payload.response.PostResponse;
import com.sql.authentication.repository.*;
import com.sql.authentication.service.UserRequest.UserRequestService;
import com.sql.authentication.serviceimplementation.auth.UserDetailsImpl;
import com.sql.authentication.utils.FileUpload;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class UserRequestServiceImpl implements UserRequestService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EcategoryRepository ecategoryRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopRegistrationRepository shopRegistrationRepository;
    @Autowired
    private UserRequestRepository userRequestRepository;
    @Autowired
    private ModelMapper  modelMapper;
    @Autowired
    private FileUpload fileUpload;
    @Override
    public UserRequest post(UserRequestDto dto){
        UserRequest userRequest=new UserRequest();

        User user=userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));
        Ecategory ecategory=ecategoryRepository.findByName(dto.getCategories())
                .orElseThrow(()->new RuntimeException("Category not found"));

        List<String> proofFiles = new ArrayList<>();
        if(dto.getImages() !=null && !dto.getImages().isEmpty()){
            for (MultipartFile file : dto.getImages()) {
                if (!file.isEmpty()) {
                    String fileName = fileUpload.uniqueFileName("Proof", file);
                    proofFiles.add(fileName);
                }
            }
        }
      
        userRequest.setName(dto.getName());
        userRequest.setUserId(user);
        userRequest.setBrand(dto.getBrand());
        userRequest.setEcategory(ecategory);
        userRequest.setImages(proofFiles);
        userRequest.setModel(dto.getModel());
        userRequest.setPostCondition(dto.getPostCondition());
        userRequest.setMaxAmount(Long.valueOf(dto.getMaxAmount()));
        userRequest.setMinAmount(Long.valueOf(dto.getMinAmount()));
        userRequestRepository.save(userRequest);
        return userRequest;
    }

    //Post
    public List<PostResponse> userPost(String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        return userRequestRepository.findByUserId(user).stream()
                .map(data->modelMapper.map(data,PostResponse.class)).toList();
    }

    @Override
    public List<PostResponse> allPostList() {

        return userRequestRepository.findAll().stream()
                .map(data->modelMapper.map(data,PostResponse.class)).toList();
       
    }

}

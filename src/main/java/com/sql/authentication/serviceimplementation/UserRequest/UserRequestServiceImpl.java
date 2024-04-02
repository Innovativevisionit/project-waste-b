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
    public UserRequest post(UserRequestDto dto,HttpSession session){
        UserRequest userRequest=new UserRequest();
        UserDetailsImpl userDetails=getUserDetails(session);
        System.out.println("Helo" + userDetails.getEmail());
        User user=userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));
        Ecategory ecategory=ecategoryRepository.findByName(dto.getCategories())
                .orElseThrow(()->new RuntimeException("Category not found"));
        List<ShopRegistration> shop;
//        if(dto.getAllShop().equalsIgnoreCase("Yes")){
//            shop=shopRepository.findByRegistration_Ecategory(ecategory);
//        }else{
//            shop=shopRepository.findByShopCodeIn(dto.getShopId());
//        }
        if(dto.getAllShop().equalsIgnoreCase("Yes")){
            shop=shopRegistrationRepository.findByEcategoryAndStatus(ecategory, StatusEnum.approve.getValue());
        }else{
            shop=shopRepository.findByShopCodeIn(dto.getShopId()).stream()
                    .map(data->modelMapper.map(data.getRegistration(),ShopRegistration.class)).toList();
        }
        List<String> proofFiles = new ArrayList<>();
        for (MultipartFile file : dto.getImages()) {
            if (!file.isEmpty()) {
                String fileName = fileUpload.uniqueFileName("Post", file);
                proofFiles.add(fileName);
            }
        }
        userRequest.setUserId(user);
        userRequest.setBrand(dto.getBrand());
        userRequest.setEcategory(ecategory);
        userRequest.setImages(proofFiles);
        userRequest.setModel(dto.getModel());
        userRequest.setMaxAmount(Long.valueOf(dto.getMaxAmount()));
        userRequest.setMinAmount(Long.valueOf(dto.getMinAmount()));
        userRequest.setAllShop(dto.getAllShop());
        userRequest.setShop(shop);
        userRequestRepository.save(userRequest);
        return userRequest;
    }

    public UserDetailsImpl getUserDetails(HttpSession session) {
        UserDetailsImpl userDetails = (UserDetailsImpl) session.getAttribute("user");
        System.out.println(userDetails + "Hellooo");
        if (userDetails != null) {
            return userDetails;
        } else {
            throw new RuntimeException("User not found in session");
        }
    }
    //Post
    public List<PostResponse> userPost(HttpSession session){
        UserDetailsImpl userDetails=getUserDetails(session);
        User user=userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));
        return userRequestRepository.findByUserId(user).stream()
                .map(data->modelMapper.map(data,PostResponse.class)).toList();
    }


}

package com.sql.authentication.serviceimplementation.UserRequest;

import com.sql.authentication.dto.UserRequestDto;
import com.sql.authentication.model.*;
import com.sql.authentication.repository.EcategoryRepository;
import com.sql.authentication.repository.ShopRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.repository.UserRequestRepository;
import com.sql.authentication.service.UserRequest.UserRequestService;
import com.sql.authentication.serviceimplementation.auth.UserDetailsImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
    private UserRequestRepository userRequestRepository;
    @Override
    public UserRequest post(UserRequestDto dto,HttpSession session){
        UserRequest userRequest=new UserRequest();
        UserDetailsImpl userDetails=getUserDetails(session);
        System.out.println("Helo" + userDetails.getEmail());
        User user=userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));
        Ecategory ecategory=ecategoryRepository.findByName(dto.getCategories())
                .orElseThrow(()->new RuntimeException("Category not found"));
        List<Shop> shop;
        if(dto.getAllShop().equalsIgnoreCase("Yes")){
            shop=shopRepository.findByRegistration_Ecategories(ecategory);
        }else{
            shop=shopRepository.findByShopCodeIn(dto.getShopId());
        }
        userRequest.setUserId(user);
        userRequest.setBrand(dto.getBrand());
        userRequest.setEcategory(ecategory);
        userRequest.setModel(dto.getModel());
        userRequest.setMaxAmount(Long.valueOf(dto.getMaxAmount()));
        userRequest.setMaxAmount(Long.valueOf(dto.getMinAmount()));
        userRequest.setAllShop(dto.getAllShop());
//        userRequest.setShop(shop);
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

}

package com.sql.authentication.serviceimplementation.shop;

import com.sql.authentication.Enum.StatusEnum;
import com.sql.authentication.dto.ShopRegisterDto;
import com.sql.authentication.dto.ShopUpdateDto;
import com.sql.authentication.model.*;
import com.sql.authentication.payload.response.ShopDto;
import com.sql.authentication.payload.response.ShopResponse;
import com.sql.authentication.repository.*;
import com.sql.authentication.service.Shop.ShopService;
import com.sql.authentication.serviceimplementation.auth.UserDetailsImpl;
import com.sql.authentication.utils.FetchAuthEmpId;
import com.sql.authentication.utils.FileUpload;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopRegistrationRepository shopRegistrationRepository;
    @Autowired
    private EcategoryRepository ecategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FileUpload fileUpload;
    @Autowired
    private FetchAuthEmpId fetchAuthEmpId;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRequestRepository userRequestRepository;

    public Object shopRegistration(ShopRegisterDto data){

        ShopRegistration shopRegistration=new ShopRegistration();
        List<String> proofFiles = new ArrayList<>();

        for (MultipartFile file : data.getFiles()) {
            if (!file.isEmpty()) {
                String fileName = fileUpload.uniqueFileName("Proof", file);
                proofFiles.add(fileName);
            }
        }

        User user=userRepository.findByEmail(data.getEmail()).orElse(null);

        if(user==null){
            throw new RuntimeException(data.getUserId()+ "is not found.");
        }
        Boolean alreadyExist = shopRegistrationRepository.existsByUserId(user);
        if(alreadyExist){
            throw new RuntimeException("Already Registered");
        }
        Ecategory ecategory = ecategoryRepository.findByName(data.getCategory())
            .orElseThrow(() -> new RuntimeException(data.getCategory()+ "is not found."));
        shopRegistration.setShopName(data.getShopName());
        System.out.println("data");

        shopRegistration.setContactNo(Long.parseLong(data.getContactNo()));
        shopRegistration.setImages(proofFiles);
        shopRegistration.setUserId(user);
        shopRegistration.setEcategory(ecategory);
        shopRegistration.setLocation(data.getLocation());
        shopRegistration.setRecyclingMethod(data.getRecyclingMethod());
        shopRegistration.setHandlingHazard(data.getHandlingHazard());
        shopRegistration.setWebsite(data.getWebsite());
        shopRegistration.setSocialLink(data.getSocialLink());
        shopRegistration.setStatus(StatusEnum.pending.getValue());
        
        return shopRegistrationRepository.save(shopRegistration);

    }

    public List<ShopDto> pendingShop(String status){
        
        return shopRegistrationRepository.findByStatus(status).stream()
        .map(data->modelMapper.map(data,ShopDto.class)).toList();
    }

    public List<ShopDto> approvedShop(String status){
        
        return shopRegistrationRepository.findByStatus(status).stream()
        .map(data->modelMapper.map(data,ShopDto.class)).toList();
    }

    public List<ShopRegistration> registerRequest(String status){
        return shopRegistrationRepository.findByStatus(status.substring(0, 1).toUpperCase() + status.substring(1));
    }

    public ShopRegistration updateShopRequest(ShopUpdateDto update){
        System.out.println(update);
        ShopRegistration registration=shopRegistrationRepository.findById(update.getId())
                .orElseThrow(()->new RuntimeException("Id not found"));
        if(!registration.getStatus().equalsIgnoreCase(StatusEnum.pending.getValue())){
            throw new NoSuchElementException("Already Status changed");
        }
        if(update.getStatus().equalsIgnoreCase(StatusEnum.reject.getValue())){
            registration.setStatus(StatusEnum.reject.getValue());
//            registration.setReason(update.getReason());
            registration.setApprovedDate(LocalDate.now());
            shopRegistrationRepository.save(registration);
        } else if (update.getStatus().equalsIgnoreCase(StatusEnum.approve.getValue())) {
            registration.setStatus(StatusEnum.approved.getValue());
//            registration.setReason(update.getReason());
            registration.setApprovedDate(LocalDate.now());
            shopRegistrationRepository.save(registration);
            User user=userRepository.findByEmail(registration.getUserId().getEmail())
                    .orElseThrow(()->new RuntimeException("Not found"));
            Set<Role> roles=new HashSet<>();
            Role role=roleRepository.findByName("Shop Owner").orElseThrow(()-> new RuntimeException("Role Not found"));
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }
       return registration;

    }
    
    //Approve or Reject the Shop Request
    // public ShopRegistration updateShopRequest(ShopUpdateDto update){
    //     ShopRegistration registration=shopRegistrationRepository.findById(update.getId())
    //             .orElseThrow(()->new RuntimeException("Id not found"));
    //     if(!registration.getStatus().equalsIgnoreCase(StatusEnum.pending.getValue())){
    //         throw new NoSuchElementException("Already Status changed");
    //     }
    //     if(update.getStatus().equalsIgnoreCase(StatusEnum.reject.getValue())){
    //         registration.setStatus(StatusEnum.rejected.getValue());
    //         registration.setReason(update.getReason());
    //         shopRegistrationRepository.save(registration);
    //     } else if (update.getStatus().equalsIgnoreCase(StatusEnum.approve.getValue())) {
    //         registration.setStatus(StatusEnum.approved.getValue());
    //         registration.setApprovedDate(LocalDate.now());
    //         Shop shop=new Shop();
    //         String shopCode=shopRepository.generateNextShopCode();
    //         shop.setShopCode(shopCode);
    //         shop.setApprovedDate(LocalDate.now());
    //         shop.setRegistration(registration);
    //         shopRepository.save(shop);
    //         shopRegistrationRepository.save(registration);
    //         User user=userRepository.findByEmail(registration.getUserId().getEmail())
    //                 .orElseThrow(()->new RuntimeException("Not found"));
    //         Set<Role> roles=new HashSet<>();
    //         Role role=roleRepository.findByName("Shop Owner").orElseThrow(()-> new RuntimeException("Role Not found"));
    //         roles.add(role);
    //         user.setRoles(roles);
    //         userRepository.save(user);
    //     }
    //    return registration;

    // }

    private String shopCode(){
//        int lastCode=shopRepository.findMaxNumericPart();
//        int nextNumericPart = (lastCode != 0) ? lastCode + 1 : 1;
//        return "SH" + String.format("%05d", nextNumericPart);
        return  "Hello";
    }
    public List<ShopRegistration> shopList(HttpSession session){
        return shopRegistrationRepository.findAll();
//                .stream()
//                .map(data->modelMapper.map(data,ShopResponse.class)).toList();
//        UserDetailsImpl details=getUserDetails(session);
//        User user=userRepository.findByEmail(details.getEmail()).orElse(null);
//        if(user==null){
//            throw new RuntimeException("User is not found.");
//        }
//        return shopRepository.findAllOrderByLocation(user.getLocation())
//        .stream()
//        .map(data->{
//            ShopResponse shopResponse=modelMapper.map(data.getRegistration(),ShopResponse.class);
//            shopResponse.setShopCode(data.getShopCode());
//            return shopResponse;
//        }).toList();

//        return shopRegistrationRepository.
//                findAllOrderByLocation(user.getLocation(),StatusEnum.approve.getValue()).stream().map(data->modelMapper.map(data,ShopResponse.class)).toList();

    }

    public UserDetailsImpl getUserDetails(HttpSession session) {
        UserDetailsImpl userDetails = (UserDetailsImpl) session.getAttribute("user");
        if (userDetails != null) {
            return userDetails;
        } else {
            throw new RuntimeException("User not found in session");
        }
    }

    @Override
    public UserRequest sendPostToShop(int shopId,String postname) {
        
        System.out.println("shopid "+shopId);
        System.out.println("postname "+postname);
        UserRequest userRequest = userRequestRepository.findByName(postname);  //post name unique

        userRequest.setRequestedShopId(shopId);
        userRequestRepository.save(userRequest);
        return userRequest;
    }
}

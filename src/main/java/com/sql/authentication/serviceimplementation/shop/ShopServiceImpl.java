package com.sql.authentication.serviceimplementation.shop;

import com.sql.authentication.Enum.StatusEnum;
import com.sql.authentication.dto.ShopRegisterDto;
import com.sql.authentication.dto.ShopUpdateDto;
import com.sql.authentication.model.*;
import com.sql.authentication.repository.EcategoryRepository;
import com.sql.authentication.repository.ShopRegistrationRepository;
import com.sql.authentication.repository.ShopRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.service.Shop.ShopService;
import com.sql.authentication.utils.FetchAuthEmpId;
import com.sql.authentication.utils.FileUpload;
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
    private FileUpload fileUpload;
    @Autowired
    private FetchAuthEmpId fetchAuthEmpId;
    public Object shopRegistration(ShopRegisterDto data){
        ShopRegistration shopRegistration=new ShopRegistration();
        List<String> proofFiles = new ArrayList<>();
        for (MultipartFile file : data.getFiles()) {
            if (!file.isEmpty()) {
                String fileName = fileUpload.uniqueFileName("Proof", file);
                proofFiles.add(fileName);
            }
        }
        User user=userRepository.findById(data.getUserId()).orElse(null);
        if(user==null){
            throw new RuntimeException(data.getUserId()+ "is not found.");
        }

        Set<String> dataCategory = data.getCategory();
        Set<Ecategory> ecategorySet = new HashSet<>();
        dataCategory.forEach(category -> {
            Ecategory ecategory = ecategoryRepository.findByName(category)
                    .orElseThrow(() -> new RuntimeException(category+ "is not found."));
            ecategorySet.add(ecategory);
        });
        shopRegistration.setShopName(data.getShopName());
        shopRegistration.setContactNo(Long.parseLong(data.getContactNo()));
        shopRegistration.setProofFile(proofFiles);
        shopRegistration.setUserId(user);
        shopRegistration.setEcategories(ecategorySet);
        shopRegistration.setLocation(data.getLocation());
        shopRegistration.setRecyclingMethod(data.getRecyclingMethod());
        shopRegistration.setHandlingHazard(data.getHandlingHazard());
        shopRegistration.setWebsite(data.getWebsite());
        shopRegistration.setSocialLink(data.getSocialLink());
        shopRegistration.setStatus(StatusEnum.pending.getValue());
        return shopRegistrationRepository.save(shopRegistration);

    }
    public List<ShopRegistration> registerRequest(String status){
        return shopRegistrationRepository.findByStatus(status.substring(0, 1).toUpperCase() + status.substring(1));
    }
    //Approve or Reject the Shop Request
    public ShopRegistration updateShopRequest(ShopUpdateDto update){
        ShopRegistration registration=shopRegistrationRepository.findById(update.getId())
                .orElseThrow(()->new RuntimeException("Id not found"));
        if(!registration.getStatus().equalsIgnoreCase(StatusEnum.pending.getValue())){
            throw new NoSuchElementException("Already Status changed");
        }
        if(update.getStatus().equalsIgnoreCase(StatusEnum.reject.getValue())){
            registration.setStatus(StatusEnum.rejected.getValue());
            registration.setReason(update.getReason());
            shopRegistrationRepository.save(registration);
        } else if (update.getStatus().equalsIgnoreCase(StatusEnum.approve.getValue())) {
            registration.setStatus(StatusEnum.approved.getValue());
            Shop shop=new Shop();
            String shopCode=shopRepository.generateNextShopCode();
            shop.setShopCode(shopCode);
            shop.setApprovedDate(LocalDate.now());
            shop.setRegistration(registration);
            shopRepository.save(shop);
            shopRegistrationRepository.save(registration);
        }
       return registration;

    }
    private String shopCode(){
//        int lastCode=shopRepository.findMaxNumericPart();
//        int nextNumericPart = (lastCode != 0) ? lastCode + 1 : 1;
//        return "SH" + String.format("%05d", nextNumericPart);
        return  "Hello";
    }
    public List<Shop> shopList(){
        return shopRepository.findAllOrderByLocation("Coimbatore");
    }
}

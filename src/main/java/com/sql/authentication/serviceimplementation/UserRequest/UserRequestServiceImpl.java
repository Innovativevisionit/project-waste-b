package com.sql.authentication.serviceimplementation.UserRequest;

import com.sql.authentication.Enum.StatusEnum;
import com.sql.authentication.dto.PostDto;
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
import java.util.stream.Collectors;

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
        userRequest.setStatus("pending");
        userRequest.setRequestedShopId(0);
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

    @Override
    public PostResponse getById(Integer id) {
        
        Optional<UserRequest> data = userRequestRepository.findById(id);

    PostResponse postResponse = new PostResponse();
    if (data.isPresent()) {
        postResponse = modelMapper.map(data.get(), PostResponse.class);
    }
    return postResponse;
    }

    @Override
    public List<PostResponse> getCategoryBasedpost(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ShopRegistration registration = shopRegistrationRepository.findByUserId(user);
        List<UserRequest> postList = new ArrayList<>(); // Initialize list outside if block

        if(registration != null) {
            Optional<Ecategory> ecategory = ecategoryRepository.findById(registration.getEcategory().getId());
            if(ecategory.isPresent()) {
                postList = userRequestRepository.findByEcategory(ecategory.get());
            }
        }

        return postList.stream()
                .map(data -> modelMapper.map(data, PostResponse.class))
                .toList();
    }


    @Override
    public UserRequest acceptPost(PostDto postDto) {
        
        UserRequest userRequest =  userRequestRepository.findById(postDto.getPostId()).get();

        if(postDto.getStatus().equals("reject")){
            userRequest.setStatus("rejected");
            userRequest.setApprovedBy(null);
            userRequest.setDeliverymanName(null);
            userRequest.setReason(postDto.getReason());
        }else{
            userRequest.setStatus("approved");
            User user=userRepository.findByEmail(postDto.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));
        userRequest.setApprovedBy(user.getId());
        userRequest.setDeliverymanName(postDto.getDeliveryMan());
        userRequest.setReason(postDto.getReason());
        }
        return userRequestRepository.save(userRequest);
    }

    @Override
    public List<String> getPendingPostByUser(String email) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));

        List<UserRequest> result = userRequestRepository.findByUserIdAndStatus(user,"pending");
        
        List<String> data = result.stream().map(UserRequest::getName)
                            .collect(Collectors.toList());
        return data;
    }

    @Override
    public List<PostResponse> getRequestedPost(String email) {
        User user=userRepository.findByEmail(email)
        .orElseThrow(()->new RuntimeException("User not found"));

        ShopRegistration registration = shopRegistrationRepository.findByUserId(user);

        List<UserRequest> postList = userRequestRepository.findByRequestedShopIdAndStatus(registration.getId(),"pending");
        return postList.stream()
        .map(data->modelMapper.map(data,PostResponse.class)).toList();
    }

    @Override
    public Map<String, Long> getDashboardCount() {
       
        Long pendingPostCount = userRequestRepository.countByStatus("pending");
        Long approvedPostCount = userRequestRepository.countByStatus("approved");
        Long pendingShopCount = shopRegistrationRepository.countByStatus("Pending");
        Long approvedShopCount = shopRegistrationRepository.countByStatus("Approved");
        Map<String,Long> totalCounts = new HashMap<>();
        totalCounts.put("pendingPostCount", pendingPostCount);   //pending post count
        totalCounts.put("approvedPostCount", approvedPostCount);    //approved post count
        totalCounts.put("pendingShopCount", pendingShopCount);  //pending shop count
        totalCounts.put("approvedShopCount", approvedShopCount);    //approved post count
        return totalCounts;
    }

    @Override
    public List<PostResponse> getUserRequestedPost(String email) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));

//        ShopRegistration registration = shopRegistrationRepository.findByUserId(user);

        List<UserRequest> postList = userRequestRepository.findByUserIdAndStatus(user,"pending");
        return postList.stream()
                .map(data->modelMapper.map(data,PostResponse.class)).toList();
    }

}

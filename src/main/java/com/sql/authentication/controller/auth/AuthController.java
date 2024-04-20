package com.sql.authentication.controller.auth;

import com.sql.authentication.dto.UserResponseDto;
import com.sql.authentication.jwt.JwtUtils;
import com.sql.authentication.model.Deliveryman;
import com.sql.authentication.model.Role;
import com.sql.authentication.model.User;
import com.sql.authentication.payload.request.SignInRequest;
import com.sql.authentication.payload.request.SignUpRequest;
import com.sql.authentication.payload.response.ApiResponse;
import com.sql.authentication.payload.response.LoginResponse;
import com.sql.authentication.repository.DeliveryRepository;
import com.sql.authentication.repository.RoleRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.serviceimplementation.auth.UserDetailsImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest loginRequest, HttpSession session) {

        System.out.println(loginRequest);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        session.setAttribute("user", userDetails);
        System.out.println(session.getAttribute("user"));

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getEmail());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new LoginResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles,jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            System.out.println(signUpRequest);
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Username is already taken!"));
            }
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Email is already in use!"));
            }
            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),signUpRequest.getLocation());
            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName("Employee")
                        .orElseThrow(() -> new RuntimeException("Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    Role adminRole = roleRepository.findByName(role)
                            .orElseThrow(() -> new RuntimeException("Role is not found."));
                    roles.add(adminRole);
                });
            }

            user.setRoles(roles);
            user.setAge(signUpRequest.getAge());
            user.setMobileNo(signUpRequest.getMobileNo());
            System.out.println(user);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "User registered successfully!", user));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false,e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,e.getMessage()));
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse(true,"You've been signed out!"));
    }

    @GetMapping("getUserDetails")
    public UserResponseDto getUserDetails(@RequestParam String email) {
        UserResponseDto userResponseDto = new UserResponseDto();
        User user = userRepository.findByEmail(email).get();
        userResponseDto = modelMapper.map(user, UserResponseDto.class);
        String roles = user.getRoles().stream()
                            .map(Role::getName) // Assuming Role has a method getName() to get the role name
                            .collect(Collectors.joining(", "));
        userResponseDto.setRolesName(roles);
        return userResponseDto;

    }
    
    @PostMapping("/store-deliveryman")
    public String storedelivery(@RequestBody Deliveryman deliveryman) {
        
        deliveryRepository.save(deliveryman);
        return "saved";
    }
    
    @GetMapping("getDeliveryMan")
    public List<String> getDeliveryMan() {
        return deliveryRepository.findAll().stream().map(Deliveryman::getName)
        .collect(Collectors.toList());
    }
    
}

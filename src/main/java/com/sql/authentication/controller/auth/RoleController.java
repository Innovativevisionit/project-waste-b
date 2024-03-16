package com.sql.authentication.controller.auth;

import com.sql.authentication.model.Role;
import com.sql.authentication.payload.response.ApiResponse;
import com.sql.authentication.service.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody Role role) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "Inserted Successfully", roleService.save(role)));
        }catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }

}

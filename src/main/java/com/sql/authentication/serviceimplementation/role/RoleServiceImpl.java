package com.sql.authentication.serviceimplementation.role;

import com.sql.authentication.model.Role;
import com.sql.authentication.repository.RoleRepository;
import com.sql.authentication.service.RoleService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        Optional<Role> exist = roleRepository.findByName(role.getName());
        if (exist.isPresent() && exist.get().getName().equals(role.getName())) {
            throw new IllegalArgumentException("Role name already exists");
        }
        return roleRepository.save(role);
    }
}

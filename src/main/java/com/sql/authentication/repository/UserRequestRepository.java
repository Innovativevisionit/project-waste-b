package com.sql.authentication.repository;

import com.sql.authentication.model.User;
import com.sql.authentication.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRequestRepository extends JpaRepository<UserRequest,Integer> {
    List<UserRequest> findByUserId(User user);
}

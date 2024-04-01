package com.sql.authentication.repository;

import com.sql.authentication.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestRepository extends JpaRepository<UserRequest,Integer> {
}

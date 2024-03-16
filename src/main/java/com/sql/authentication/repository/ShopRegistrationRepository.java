package com.sql.authentication.repository;

import com.sql.authentication.model.ShopRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRegistrationRepository extends JpaRepository<ShopRegistration,Integer> {
    List<ShopRegistration> findByStatus(String status);

}

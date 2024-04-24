package com.sql.authentication.repository;

import com.sql.authentication.model.Ecategory;
import com.sql.authentication.model.User;
import com.sql.authentication.model.UserRequest;
import com.sql.authentication.payload.response.PostResponse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRequestRepository extends JpaRepository<UserRequest,Integer> {

    List<UserRequest> findByUserId(User user);

    Optional<UserRequest> findById(Integer id);

    List<UserRequest> findByEcategory(Ecategory ecategory);

    Long countByUserIdAndStatus(User user,String status);

    UserRequest findByName(String name);

    List<UserRequest> findByUserIdAndStatus(User user ,String status);

    List<UserRequest> findByRequestedShopId(int shopId);

    List<UserRequest> findByRequestedShopIdAndStatus(int id, String pending);
}

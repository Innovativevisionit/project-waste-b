package com.sql.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sql.authentication.model.Deliveryman;

public interface DeliveryRepository extends JpaRepository<Deliveryman,Long>{

}

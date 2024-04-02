package com.sql.authentication.repository;

import com.sql.authentication.model.Ecategory;
import com.sql.authentication.model.Shop;
import com.sql.authentication.model.ShopRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRegistrationRepository extends JpaRepository<ShopRegistration,Integer> {
    List<ShopRegistration> findByStatus(String status);
    List<ShopRegistration> findByStatusAndLocationOrderByLocationDesc(String status,String location);
    //
    List<ShopRegistration> findByStatusAndLocationNotOrderByLocationDesc(String status,String location);
    default List<ShopRegistration> findAllOrderByLocation(String location,String status) {
        List<ShopRegistration> chennaiShops = findByStatusAndLocationOrderByLocationDesc(status,location);
        System.out.println(chennaiShops);
        List<ShopRegistration> otherShops = findByStatusAndLocationNotOrderByLocationDesc(status,location);
        chennaiShops.addAll(otherShops);
        return chennaiShops;
    }


    List<ShopRegistration> findByEcategoryAndStatus(Ecategory ecategory,String status);
}

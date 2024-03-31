package com.sql.authentication.repository;

import com.sql.authentication.model.Ecategory;
import com.sql.authentication.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ShopRepository extends JpaRepository<Shop,Long> {
    @Query("SELECT COALESCE(MAX(s.shopCode), 0) FROM Shop s")
    String findMaxShopCode();
    List<Shop> findByRegistration_LocationOrderByRegistration_LocationDesc(String location);
//
    List<Shop> findByRegistration_LocationNotOrderByRegistration_LocationDesc(String location);
    List<Shop> findByRegistration_Ecategories(Ecategory eCategory);
    List<Shop> findByShopCodeIn(List<String> shop);

    default List<Shop> findAllOrderByLocation(String location) {
        List<Shop> chennaiShops = findByRegistration_LocationOrderByRegistration_LocationDesc(location);
        List<Shop> otherShops = findByRegistration_LocationNotOrderByRegistration_LocationDesc(location);
        chennaiShops.addAll(otherShops);
        return chennaiShops;
    }

    default String generateNextShopCode() {
        String maxShopCode = findMaxShopCode();
        int maxShopCodeInt;
        if(maxShopCode.equals("0")){
            maxShopCodeInt = Integer.parseInt(maxShopCode);
        }else{
           maxShopCodeInt=Integer.parseInt(maxShopCode.substring(2,7));
        }
        maxShopCodeInt++;
        return String.format("SH%05d", maxShopCodeInt);
    }


}

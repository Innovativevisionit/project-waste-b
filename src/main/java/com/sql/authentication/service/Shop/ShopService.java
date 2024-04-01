package com.sql.authentication.service.Shop;

import com.sql.authentication.dto.ShopRegisterDto;
import com.sql.authentication.dto.ShopUpdateDto;
import com.sql.authentication.model.Shop;
import com.sql.authentication.model.ShopRegistration;

import java.util.List;

public interface ShopService {
    Object shopRegistration(ShopRegisterDto shopRegistration);
    List<ShopRegistration> registerRequest(String status);
    ShopRegistration updateShopRequest(ShopUpdateDto update);
    List<ShopRegistration> shopList();
}

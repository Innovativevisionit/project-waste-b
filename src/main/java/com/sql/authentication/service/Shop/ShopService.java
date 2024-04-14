package com.sql.authentication.service.Shop;

import com.sql.authentication.dto.ShopRegisterDto;
import com.sql.authentication.dto.ShopUpdateDto;
import com.sql.authentication.model.Shop;
import com.sql.authentication.model.ShopRegistration;
import com.sql.authentication.payload.response.ShopDto;
import com.sql.authentication.payload.response.ShopResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ShopService {
    Object shopRegistration(ShopRegisterDto shopRegistration);
    List<ShopRegistration> registerRequest(String status);
    List<ShopDto> pendingShop(String status);
    List<ShopDto> approvedShop(String status);
    ShopRegistration updateShopRequest(ShopUpdateDto update);
    List<ShopRegistration> shopList(HttpSession session);
}

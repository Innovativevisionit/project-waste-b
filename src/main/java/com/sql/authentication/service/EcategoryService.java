package com.sql.authentication.service;

import com.sql.authentication.model.Ecategory;

public interface EcategoryService {
    Object add(Ecategory data);
    Object list();
    Object activeList();
}

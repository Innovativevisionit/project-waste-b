package com.sql.authentication.controller.master;

import com.sql.authentication.model.Ecategory;
import com.sql.authentication.service.EcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecategory")
public class EcategoryController {
    @Autowired
    private EcategoryService ecategoryService;
    @PostMapping("/store")
    public Object store(@RequestBody Ecategory ecategory){
        return ecategoryService.add(ecategory);
    }
    @GetMapping("/list")
    public Object get(){
        return ecategoryService.list();
    }

    @GetMapping("/activeList")
    public Object activeList(){
        return ecategoryService.activeList();
    }
}

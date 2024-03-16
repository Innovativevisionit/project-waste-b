package com.sql.authentication.serviceimplementation.master;

import com.sql.authentication.model.Ecategory;
import com.sql.authentication.repository.EcategoryRepository;
import com.sql.authentication.service.EcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EcategoryServiceImpl implements EcategoryService {
    @Autowired
    private EcategoryRepository ecategoryRepository;

    public Object add(Ecategory data){
        try {
            if(ecategoryRepository.existsByName(data.getName())){
                throw new RuntimeException("Name already exists");
            }
            ecategoryRepository.save(data);
            return ResponseEntity.ok().body(data);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Object list(){
        try {
            List<Ecategory> list=ecategoryRepository.findAll();
            return ResponseEntity.ok().body(list);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Object activeList(){
        try {
            List<Ecategory> list=ecategoryRepository.findAllByStatus(1);
            return ResponseEntity.ok().body(list);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

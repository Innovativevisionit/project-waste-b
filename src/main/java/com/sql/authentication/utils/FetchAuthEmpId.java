package com.sql.authentication.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class FetchAuthEmpId {
//    @Autowired
//    private AuditorAware<String> auditorAware;
//
//    public String empId(){
//        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();
//        if(currentAuditor.isEmpty()){
//            throw  new RuntimeException("Employee Id is Empty");
//        }
//        return currentAuditor.get();
//    }
}

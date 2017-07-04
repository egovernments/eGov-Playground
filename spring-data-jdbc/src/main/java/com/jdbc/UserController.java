package com.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc.service.UserServiceJDBC;

@RestController
public class UserController {
    
    @Autowired
    private UserServiceJDBC userServiceJDBC;
    
    @RequestMapping("/jdbc")  
    public String create() {
        userServiceJDBC.create();
        return "data persisted";
        
    }
    
}

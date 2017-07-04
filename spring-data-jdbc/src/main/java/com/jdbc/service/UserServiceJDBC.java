package com.jdbc.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdbc.entity.User;
import com.jdbc.repository.UserRepositoryJDBC;

@Service
public class UserServiceJDBC {
    
    @Autowired
    private UserRepositoryJDBC userRepositoryJDBC;
    
    @Transactional
    public User create() {
        User user = new User();
        user.setActive(false);
        user.setName("Test Name");
        user.setId(1);
        return userRepositoryJDBC.save(user);
    }


}

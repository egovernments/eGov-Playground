package org.egov.process.service;

import org.egov.process.entity.User;
import org.egov.process.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<User> getUsersByNameLike(String name) {
        return userRepository.findByFirstNameContains(name);
    }
}

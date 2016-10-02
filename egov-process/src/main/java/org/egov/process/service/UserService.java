package org.egov.process.service;

import java.util.List;

import org.egov.process.entity.User;
import org.egov.process.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional
	 public User create(final User user) {
	return userRepository.save(user);
 } 
	 @Transactional
	 public User update(final User user) {
	return userRepository.save(user);
	  } 
	public List<User> findAll() {
	 return userRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	   }
	public User findOne(Long id){
	return userRepository.findOne(id);
	}
	public List<User> search(User user){
	return userRepository.findAll();
	}
}

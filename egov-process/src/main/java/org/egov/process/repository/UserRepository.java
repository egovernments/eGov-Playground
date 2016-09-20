package org.egov.process.repository;

import org.egov.process.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);

    User findByUserName(String userName);

    List<User> findByFirstNameContains(String name);

}

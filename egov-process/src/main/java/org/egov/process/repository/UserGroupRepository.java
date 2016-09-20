package org.egov.process.repository;

import org.egov.process.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    UserGroup findByName(String name);

    List<UserGroup> findByNameContains(String name);
}

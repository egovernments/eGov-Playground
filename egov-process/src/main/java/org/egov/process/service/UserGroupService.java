package org.egov.process.service;

import org.egov.process.entity.UserGroup;
import org.egov.process.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    public UserGroup getUserGroupByName(String name) {
        return userGroupRepository.findByName(name);
    }

    public List<UserGroup> getUserGroupsByNameLike(String name) {
        return userGroupRepository.findByNameContains(name);
    }

}

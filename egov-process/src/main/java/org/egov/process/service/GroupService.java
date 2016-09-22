package org.egov.process.service;

import org.egov.process.entity.Group;
import org.egov.process.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group getGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    public List<Group> getGroupsByNameLike(String name) {
        return groupRepository.findByNameContains(name);
    }

}

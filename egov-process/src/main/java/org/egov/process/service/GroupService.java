package org.egov.process.service;

import java.util.List;

import org.egov.process.entity.Group;
import org.egov.process.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public Group create(final Group group) {
		return groupRepository.save(group);
	}

	@Transactional
	public Group update(final Group group) {
		return groupRepository.save(group);
	}

	public List<Group> findAll() {
		return groupRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	public Group findByName(String name) {
		return groupRepository.findByName(name);
	}

	public Group findOne(Long id) {
		return groupRepository.findOne(id);
	}

	public List<Group> search(Group group) {
		return groupRepository.findAll();
	}

}

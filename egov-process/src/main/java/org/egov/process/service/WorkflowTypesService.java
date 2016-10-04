package org.egov.process.service;


import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.process.entity.WorkflowTypes;
import org.egov.process.repository.WorkflowTypesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service 
@Transactional(readOnly = true)
public class WorkflowTypesService  {

	private final WorkflowTypesRepository workflowTypesRepository;
	@PersistenceContext
private EntityManager entityManager;

	@Autowired
public WorkflowTypesService(final WorkflowTypesRepository workflowTypesRepository) {
	 this.workflowTypesRepository = workflowTypesRepository;
  }

	 @Transactional
	 public WorkflowTypes create(final WorkflowTypes workflowTypes) {
	return workflowTypesRepository.save(workflowTypes);
  } 
	 @Transactional
	 public WorkflowTypes update(final WorkflowTypes workflowTypes) {
	return workflowTypesRepository.save(workflowTypes);
	  } 
	public List<WorkflowTypes> findAll() {
	 return workflowTypesRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	   }
	public WorkflowTypes findOne(Long id){
	return workflowTypesRepository.findOne(id);
	}
	public List<WorkflowTypes> search(WorkflowTypes workflowTypes){
	return workflowTypesRepository.findAll();
	}

	public WorkflowTypes findByClassName(String fullClassname) {
		return workflowTypesRepository.findByClassName(fullClassname);
		
	}
}
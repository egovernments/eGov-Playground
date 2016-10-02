package org.egov.process.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.process.entity.Department;
import org.egov.process.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service 
@Transactional(readOnly = true)
public class DepartmentService  {

	private final DepartmentRepository departmentRepository;
	@PersistenceContext
private EntityManager entityManager;

	@Autowired
public DepartmentService(final DepartmentRepository departmentRepository) {
	 this.departmentRepository = departmentRepository;
  }

	 @Transactional
	 public Department create(final Department department) {
	return departmentRepository.save(department);
  } 
	 @Transactional
	 public Department update(final Department department) {
	return departmentRepository.save(department);
	  } 
	public List<Department> findAll() {
	 return departmentRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	   }
	public Department findByName(String name){
	return departmentRepository.findByName(name);
	}
	public Department findByCode(String code){
	return departmentRepository.findByCode(code);
	}
	public Department findOne(Long id){
	return departmentRepository.findOne(id);
	}
	public List<Department> search(Department department){
	return departmentRepository.findAll();
	}
}
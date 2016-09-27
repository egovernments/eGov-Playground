package com.elasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elasticsearch.model.Employee;

@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {

	Page<Employee> findByEmployeeName(String employeeName, Pageable pageable);

}

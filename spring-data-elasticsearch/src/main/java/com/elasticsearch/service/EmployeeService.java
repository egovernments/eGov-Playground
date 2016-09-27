package com.elasticsearch.service;

import com.elasticsearch.model.Employee;

public interface EmployeeService {

	Employee save(Employee employee);
	
	Iterable<Employee> save(Iterable<Employee> employees);

	Employee findOne(String crn);

	Iterable<Employee> findAll();

	long count();

	void delete(Employee employee);
}

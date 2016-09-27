package com.elasticsearch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import com.elasticsearch.model.Employee;
import com.elasticsearch.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ElasticsearchTemplate template;
	
	public void setEmployeeRepository(EmployeeRepository employeeRepository){
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public Employee save(Employee employee){
		return employeeRepository.save(employee);
	}
	
	@Override
	public Employee findOne(String crn){
		return employeeRepository.findOne(crn);
	}
	
	@Override
	public Iterable<Employee> findAll(){
		return employeeRepository.findAll();
	}
	
	@Override
    public long count() {
        return employeeRepository.count();
    }

    @Override
    public void delete(Employee employee) {
    	employeeRepository.delete(employee);
    }

	@Override
	public Iterable<Employee> save(Iterable<Employee> employees) {
		return employeeRepository.save(employees);
	}

	

}

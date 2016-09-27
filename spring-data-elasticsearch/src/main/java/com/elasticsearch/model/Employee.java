package com.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(indexName = "employee", type = "employee" )
public class Employee {
	
	@Id
	private String id;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String employeeName;
	
	@Field(type = FieldType.Integer)
	private int employeeNumber;
	
	public Employee(){
		
	}
	
	public Employee(String id, String employeeName, int employeeNumber){
		this.id = id;
		this.employeeName = employeeName;
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

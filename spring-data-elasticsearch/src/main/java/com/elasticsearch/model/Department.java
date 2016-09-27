package com.elasticsearch.model;

public class Department {
	
	private int id;
	
	private String departmentName;
	
	private String code;
	
	public Department(){
		
	}
	
	public Department(int id, String departmentName, String code){
		this.id = id;
		this.departmentName = departmentName;
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

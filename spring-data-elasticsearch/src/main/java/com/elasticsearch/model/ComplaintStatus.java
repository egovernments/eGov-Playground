package com.elasticsearch.model;

public class ComplaintStatus {

	private String name;
	
	public ComplaintStatus(){
		
	}
	
	public ComplaintStatus(String statusName){
		this.name = statusName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

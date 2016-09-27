package com.elasticsearch.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class Citizen {
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String name;
	
	@Field(type = FieldType.Long)
	private long mobile;
	
	public Citizen(){
		
	}
	
	public Citizen(String name, long mobile){
		this.name = name;
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

}

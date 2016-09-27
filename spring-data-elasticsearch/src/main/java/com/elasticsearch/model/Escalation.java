package com.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class Escalation {
	
	public Escalation(){
		
	}
	
	public Escalation(String id, String escalatedFrom, String escalatedTo){
		this.id = id;
		this.escalatedFrom = escalatedFrom;
		this.escalatedTo = escalatedTo;
	}
	
	@Id
	private String id;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String escalatedFrom;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String escalatedTo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEscalatedFrom() {
		return escalatedFrom;
	}

	public void setEscalatedFrom(String escalatedFrom) {
		this.escalatedFrom = escalatedFrom;
	}

	public String getEscalatedTo() {
		return escalatedTo;
	}

	public void setEscalatedTo(String escalatedTo) {
		this.escalatedTo = escalatedTo;
	}
}

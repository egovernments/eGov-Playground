package com.elasticsearch.model;


import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "custom")
public class CustomEntity {

	@Id
	private String id;

	@Field(type = FieldType.String)
	private String crn;
	
	@Field(type = FieldType.String)
	private String details;

	@Field(type = FieldType.Date)
	private Date createdDate;

	@Field(type = FieldType.Date)
	private Date currentDate;

	@Field(type = FieldType.Integer)
	private int complaintDuration;

	@Field(type = FieldType.Boolean)
	private Boolean isClosed;

	@Field(type = FieldType.Object)
	private ComplaintStatus complaintStatus;

	@Field(type = FieldType.Double)
	private double lon;

	@Field(type = FieldType.Double)
	private double lat;

	@GeoPointField
	private GeoPoint complaintLocation;

	@Field(type = FieldType.Nested)
	private List<Citizen> citizens; 
	
	@Field(type = FieldType.String)
	private String title;
	
	@Field(type = FieldType.String)
	private String employeeName;
	
	@Field(type = FieldType.Integer)
	private int employeeNumber;
	
	public int getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}

	public ComplaintStatus getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(ComplaintStatus complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public int getComplaintDuration() {
		return complaintDuration;
	}

	public void setComplaintDuration(int complaintDuration) {
		this.complaintDuration = complaintDuration;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public GeoPoint getComplaintLocation() {
		return complaintLocation;
	}

	public void setComplaintLocation(GeoPoint complaintLocation) {
		this.complaintLocation = complaintLocation;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<Citizen> getCitizens() {
		return citizens;
	}

	public void setCitizens(List<Citizen> citizens) {
		this.citizens = citizens;
	}

	@Override
	public String toString(){
		return "ID: "+ this.id +"crn: "+ this.crn +"Details: " + this.details;
	}
}

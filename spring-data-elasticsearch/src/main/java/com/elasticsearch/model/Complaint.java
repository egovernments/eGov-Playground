package com.elasticsearch.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.annotations.Parent;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(indexName = "complaint",type = "complaint")
public class Complaint {

	@Id
	private String id;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String crn;
	
	@Field(type = FieldType.Date)
	private Date createdDate;
	
	@JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm'Z'")
	@Field(type = FieldType.Date, index = FieldIndex.not_analyzed, 
	format = DateFormat.date_optional_time, pattern = "yyyy-MM-dd'T'hh:mm'Z'")
	private Date currentDate;
	
	@Field(type = FieldType.Integer)
	private int complaintDuration;
	
	@Field(type = FieldType.Boolean)
	private Boolean isClosed;

	@Field(type = FieldType.Double)
	private double lon;

	@Field(type = FieldType.Double)
	private double lat;

	@GeoPointField
	private GeoPoint complaintLocation;

	@Field(type = FieldType.String)
    private String complaintStatus;
    
	
    @Field(type = FieldType.Object )
    private Department department;
    
    @Field(type = FieldType.Nested, index = FieldIndex.not_analyzed)
    private List<Escalation> escalations;
	
    @Field(type = FieldType.Object)
    private Employee assignee;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String complaintType;
    
    @Field(type = FieldType.String)
    private String details;
	
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
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

	public String getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Escalation> getEscalations() {
		return escalations;
	}

	public void setEscalations(List<Escalation> escalations) {
		this.escalations = escalations;
	}

	public Employee getAssignee() {
		return assignee;
	}

	public void setAssignee(Employee assignee) {
		this.assignee = assignee;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}

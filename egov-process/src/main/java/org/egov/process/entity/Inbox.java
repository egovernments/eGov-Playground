package org.egov.process.entity;


public class Inbox {


 private Long id;

 private String taskId;

 private String sender;

 private String natureOfWork;

 private String details;

 private String link;
 
 public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getNatureOfWork() {
		return natureOfWork;
	}
	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

}

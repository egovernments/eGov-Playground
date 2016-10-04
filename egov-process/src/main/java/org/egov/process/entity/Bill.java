package org.egov.process.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.validator.constraints.Length;
 



@Entity
@Table(name = "EG_BILL")
@SequenceGenerator(name = Bill.SEQ, sequenceName = Bill.SEQ, allocationSize = 1)
public class Bill {
	public static final String BILL_TYPE_EXPENSE="Expense";
	public static final String BILL_TYPE_CONTRACTOR="Contractor";
	private static final long serialVersionUID = 1L;
	static final String SEQ = "SEQ_BILL";

	@Id
	@GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "fundid")
	private Fund fund;
	@ManyToOne
	@JoinColumn(name = "departmentid")
	private Department department;
	@Length(max = 20)
	private String billNumber;
	private Date billDate;
	private BigDecimal billAmount;
	@Length(max = 20)
	private String billType;

	@ManyToOne
	@JoinColumn(name="createdby")
	private User createdBy;
	private Date createdDate;
	private Date lastModifiedDate;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "bill", targetEntity = BillDetails.class)
	private List<BillDetails> details;
	
	@Version
	private Long version;
	
	//workflow related .. Can move to Stateware or some Abstract class 
	@Transient
	private String message;
	@Transient
	private String taskId;
	
	public BigDecimal getBillAmount() {
		return billAmount;
	}
	public Date getBillDate() {
		return billDate;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public String getBillType() {
		return billType;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Department getDepartment() {
		return department;
	}
	public List<BillDetails> getDetails() {
		return details;
	}
	public Fund getFund() {
		return fund;
	}
	public Long getId() {
		return id;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public String getMessage() {
		return message;
	}
	public String getTaskId() {
		return taskId;
	}
	public Long getVersion() {
		return version;
	}
	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	public void setDepartment(Department department) {
		this.department = department;
	}
 
	
	public void setDetails(List<BillDetails> details) {
		this.details = details;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	public void setMessage(String message) {
		this.message = message;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	 
	public void setVersion(Long version) {
		this.version = version;
	}

}

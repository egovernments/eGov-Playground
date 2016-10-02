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
import javax.persistence.Version;

import org.hibernate.validator.constraints.Length;
 



@Entity
@Table(name = "EG_BILL")
@SequenceGenerator(name = Bill.SEQ, sequenceName = Bill.SEQ, allocationSize = 1)
public class Bill {
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

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Fund getFund() {
		return fund;
	}
	public void setFund(Fund fund) {
		this.fund = fund;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public BigDecimal getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public List<BillDetails> getDetails() {
		return details;
	}
	public void setDetails(List<BillDetails> details) {
		this.details = details;
	}
	
	
	@Length(max = 20)
	private String billNumber;
	
	
	private Date billDate;
 
	
	private BigDecimal billAmount;

	@ManyToOne
	@JoinColumn(name="createdby")
	private User createdBy;

	private Date createdDate;

	private Date lastModifiedDate;
	
	
	private String billType;

	@Version
	private Long version;
	 
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bill", targetEntity = BillDetails.class)
	private List<BillDetails> details;

}

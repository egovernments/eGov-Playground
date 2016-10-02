package org.egov.process.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="eg_department")
@SequenceGenerator(name = Department.SEQ, sequenceName = Department.SEQ, allocationSize = 1)
public class Department {
	public static final String SEQ = "SEQ_EG_FUND";

	@Id
    @GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private Long kk;
	
	
	public Long getKk() {
		return kk;
	}
	public void setKk(Long kk) {
		this.kk = kk;
	}
	@Length(max=20)
	private String name;
	
	
	@Length(max=20)
	private String code;
	
	
	private Boolean active;
	 @Version
	private Long version;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	

}
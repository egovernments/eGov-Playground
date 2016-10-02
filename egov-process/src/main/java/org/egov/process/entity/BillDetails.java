package org.egov.process.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "EG_BILLDETAILS")
@SequenceGenerator(name = BillDetails.SEQ, sequenceName = BillDetails.SEQ, allocationSize = 1)
public class BillDetails {
	 private static final long serialVersionUID = 1L;
	    static final String SEQ = "SEQ_BILLDETAILS";
	 
	    @Id
	    @GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
	    private Long id;
	    
	    @Length(max=20)
	    private String glcode;
	    
	    @Length(max=20)
	    private String coaName;
	    
	    private BigDecimal debit;
	    
	    private BigDecimal credit;
	    
	    @ManyToOne
	    @JoinColumn(name = "billid")
	    private Bill bill;
	    @Version
	    private Long version;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getGlcode() {
			return glcode;
		}
		public void setGlcode(String glcode) {
			this.glcode = glcode;
		}
		public String getCoaName() {
			return coaName;
		}
		public void setCoaName(String coaName) {
			this.coaName = coaName;
		}
		public BigDecimal getDebit() {
			return debit;
		}
		public void setDebit(BigDecimal debit) {
			this.debit = debit;
		}
		public BigDecimal getCredit() {
			return credit;
		}
		public void setCredit(BigDecimal credit) {
			this.credit = credit;
		}
		public Bill getBill() {
			return bill;
		}
		public void setBill(Bill bill) {
			this.bill = bill;
		}
		public Long getVersion() {
			return version;
		}
		public void setVersion(Long version) {
			this.version = version;
		}

}

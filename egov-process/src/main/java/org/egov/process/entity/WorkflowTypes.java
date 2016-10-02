package org.egov.process.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;



@Entity
@Table(name = "EG_WORKFLOWTYPE")
@SequenceGenerator(name = WorkflowTypes.SEQ_WORKFLOWTYPES, sequenceName = WorkflowTypes.SEQ_WORKFLOWTYPES, allocationSize = 1)
public class WorkflowTypes  {

    public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	private static final long serialVersionUID = 1L;
    static final String SEQ_WORKFLOWTYPES = "SEQ_EG_WF_TYPE";

     
    @Id
    @GeneratedValue(generator = SEQ_WORKFLOWTYPES, strategy = GenerationType.SEQUENCE)
    private Long id;
    
	
    @Length(max=120)
    private String type;
    
	
    @Length(max=120)
    private String className;
    @Length(max=120)
    
	
    private String serviceName;
    
	
    @Length(max=50)
    private String businessKey;

    private Long version;
   
    
    private String link;
    
    private String displayName;
    
    private Boolean enabled;

    public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	private boolean grouped;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

     

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

   

    public Boolean isEnabled() {
        return enabled;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isGrouped() {
        return grouped;
    }

    public void setGrouped(final boolean grouped) {
        this.grouped = grouped;
    }
}

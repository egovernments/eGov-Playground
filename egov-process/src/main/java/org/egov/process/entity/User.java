package org.egov.process.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.validator.constraints.Length;



@Entity
@Table(name = "eg_user")
public class User {

    @Id
    private Long id;
    @Length(max=20)
    
	
    private String userName;
    @Length(max=20)
    
	
    private String firstName;
    
	
    @Length(max=20)
    private String lastName;
    
	
    @Length(max=20)
    private String email;
    @Length(max=20)
    private String password;
    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}

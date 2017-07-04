package com.jdbc.entity;

import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "users")
public class User implements Persistable<Integer>{
    
    private Integer id;
    private String name;
    private boolean isActive;
    
    private transient boolean persisted;
    
    public User withPersisted(boolean persisted) {
        this.persisted = persisted;
        return this;
    }
    
    @Override
    public boolean isNew() {
        return true;
    }

    public User(int id, String name, boolean isActive) {
        // TODO Auto-generated constructor stub
    }


}

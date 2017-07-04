package com.jdbc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jdbc.entity.User;

@Repository
public class UserRepositoryJDBC extends JdbcRepository<User, Integer> {

    public UserRepositoryJDBC() {
        
        //users is the table in which data needs to be persisted
        super(ROW_MAPPER, ROW_UNMAPPER, "users");
    }
    
    public static final RowMapper<User> ROW_MAPPER = new RowMapper<User>() {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBoolean("isActive")
            );
        }
        
    };
    
    

    private static final RowUnmapper<User> ROW_UNMAPPER = new RowUnmapper<User>() {
        @Override
        public Map<String, Object> mapColumns(User user) {
            Map<String, Object> mapping = new LinkedHashMap<String, Object>();
            mapping.put("id", user.getId());
            mapping.put("name", user.getName());
            mapping.put("isActive", user.isActive());
            return mapping;
        }
    };


    public Iterable<User> findAll(Iterable<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public User postCreate(User entity, Number generatedId) {
        entity.setActive(true);
        entity.setId(1);
        entity.setName("Test Name");
        return entity.withPersisted(true);
    }


    @Override
    public boolean exists(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <S extends User> Iterable<S> save(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

}

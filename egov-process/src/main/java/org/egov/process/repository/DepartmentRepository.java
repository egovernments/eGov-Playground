package org.egov.process.repository;


import org.egov.process.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository 
public interface DepartmentRepository extends JpaRepository<Department,java.lang.Long> {

	Department findByName(String name);

	Department findByCode(String code);

}
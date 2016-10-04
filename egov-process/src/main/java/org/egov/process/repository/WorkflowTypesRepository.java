package org.egov.process.repository;


import org.egov.process.entity.WorkflowTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository 
public interface WorkflowTypesRepository extends JpaRepository<WorkflowTypes,java.lang.Long> {

	WorkflowTypes findByClassName(String className);


}
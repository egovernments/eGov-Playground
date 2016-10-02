package org.egov.process.repository;


import org.egov.process.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository 
public interface FundRepository extends JpaRepository<Fund,java.lang.Long> {

	Fund findByName(String name);

	Fund findByCode(String code);

}
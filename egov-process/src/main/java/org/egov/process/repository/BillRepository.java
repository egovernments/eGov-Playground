package org.egov.process.repository;


import org.egov.process.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository 
public interface BillRepository extends JpaRepository<Bill,java.lang.Long> {

}
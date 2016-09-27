package com.elasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elasticsearch.model.Complaint;

@Repository
public interface ComplaintRepository extends ElasticsearchRepository<Complaint, String>{

	Page<Complaint> findByCrn(String crn, Pageable pageable);
	
	Page<Complaint> findByComplaintDurationLessThan(int complaintDuration, Pageable pageable);
	
	Page<Complaint> findByCrnAndComplaintType(String crn,String complaintType,Pageable pageable);
}

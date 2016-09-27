package com.elasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.elasticsearch.model.Complaint;

public interface ComplaintService {
	
	Complaint save(Complaint complaint);
	
	Iterable<Complaint> save(Iterable<Complaint> complaints);
	
	Complaint findOne(String crn);
	
	Iterable<Complaint> findAll();

	Page<Complaint> findByCrn(String crn, Pageable pageable);
	
	Page<Complaint> findByComplaintDurationLessThan(int complaintDuration, Pageable pageable);
	
	Page<Complaint> findByCrnAndComplaintType(String crn,String complaintType, Pageable pageable);
	
	long count();

    void delete(Complaint complaint);
    
    Page<Complaint> search(SearchQuery searchQuery);
}

package com.elasticsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.elasticsearch.model.Complaint;
import com.elasticsearch.repository.ComplaintRepository;

@Service
public class ComplaintServiceImpl implements ComplaintService {

	@Autowired
	private ComplaintRepository complaintRepository;
	
	@Autowired
	private ElasticsearchTemplate template;
	
	
	public void setComplaintRepository(ComplaintRepository complaintRepository){
		this.complaintRepository = complaintRepository;
	}
	
	@Override
	public Complaint save(Complaint complaint){
		return complaintRepository.save(complaint);
	}
	
	@Override
	public Complaint findOne(String id){
		return complaintRepository.findOne(id);
	}
	
	@Override
	public Iterable<Complaint> findAll(){
		return complaintRepository.findAll();
	}
	
	@Override
    public Page<Complaint> findByCrn(String crn, Pageable pageable) {
        return complaintRepository.findByCrn(crn, pageable);
    }
	
	@Override
    public long count() {
        return complaintRepository.count();
    }

    @Override
    public void delete(Complaint complaint) {
    	complaintRepository.delete(complaint);
    }

	@Override
	public Iterable<Complaint> save(Iterable<Complaint> complaints) {
		return complaintRepository.save(complaints);
	}

	@Override
	public Page<Complaint> search(SearchQuery searchQuery) {
		return complaintRepository.search(searchQuery);
	}

	@Override
	public Page<Complaint> findByComplaintDurationLessThan(
			int complaintDuration, Pageable pageable) {
		
		return complaintRepository.findByComplaintDurationLessThan(complaintDuration, pageable);
	}

	@Override
	public Page<Complaint> findByCrnAndComplaintType(String crn,
			String complaintType, Pageable pageable) {
		return complaintRepository.findByCrnAndComplaintType(crn, complaintType,pageable);
	}
}

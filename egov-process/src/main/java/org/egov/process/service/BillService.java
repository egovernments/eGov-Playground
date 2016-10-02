package org.egov.process.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.process.entity.Bill;
import org.egov.process.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service 
@Transactional(readOnly = true)
public class BillService  {

	private final BillRepository billRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public BillService(final BillRepository billRepository) {
		this.billRepository = billRepository;
	}

	@Transactional
	public Bill create(final Bill bill) {
		return billRepository.save(bill);
	} 
	@Transactional
	public Bill update(final Bill bill) {
		return billRepository.save(bill);
	} 
	public List<Bill> findAll() {
		return billRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}
	public Bill findOne(Long id){
		return billRepository.findOne(id);
	}
	public List<Bill> search(Bill bill){
		return billRepository.findAll();
	}
}
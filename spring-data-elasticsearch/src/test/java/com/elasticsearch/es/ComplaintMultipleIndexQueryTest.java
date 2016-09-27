package com.elasticsearch.es;

import java.util.Arrays;
import java.util.List;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.elasticsearch.config.Config;
import com.elasticsearch.model.Complaint;
import com.elasticsearch.model.Department;
import com.elasticsearch.model.Employee;
import com.elasticsearch.model.Escalation;
import com.elasticsearch.service.ComplaintService;
import com.elasticsearch.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class }, loader = AnnotationConfigContextLoader.class)
public class ComplaintMultipleIndexQueryTest {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private Client client;

	private final Escalation escalation1 = new Escalation("01", "Clerk", "ASSISTANT");
	private final Escalation escalation2 = new Escalation("02", "ASSISTANT", "Manager");
	private final Escalation escalation3 = new Escalation("03", "Manager", "COMMISSIONER");

	@Before
	public void before(){
		elasticsearchTemplate.deleteIndex(Employee.class);
		elasticsearchTemplate.createIndex(Employee.class);
		elasticsearchTemplate.putMapping(Employee.class);
		elasticsearchTemplate.refresh(Employee.class);

		elasticsearchTemplate.deleteIndex(Complaint.class);
		elasticsearchTemplate.createIndex(Complaint.class);
		elasticsearchTemplate.putMapping(Complaint.class);
		elasticsearchTemplate.refresh(Complaint.class);

		Employee employee1 = new Employee("001", "Amar", 100);
		Employee employee2 = new Employee("002", "Ravi", 200);
		Employee employee3 = new Employee("003", "Arun", 300);

		List<Employee> employees = Arrays.asList(employee1,employee2,employee3);

		employeeService.save(employees);

		Complaint complaint1 = new Complaint();
		complaint1.setId("00001");
		complaint1.setCrn("00010-2016-HG");
		complaint1.setCreatedDate(new DateTime(2016,05,27,0,0).toDate());
		complaint1.setCurrentDate(new DateTime().toDate());
		complaint1.setComplaintDuration(10);
		complaint1.setIsClosed(false);
		complaint1.setLat(15.7980339000101);
		complaint1.setLon(78.05202245);
		complaint1.setComplaintLocation(new GeoPoint(15.7980339000101, 78.05202245));
		complaint1.setComplaintStatus("REGISTERED");
		complaint1.setDepartment(new Department(1, "Accounts", "A"));
		complaint1.setAssignee(employee1);
		complaint1.setEscalations(Arrays.asList(escalation1,escalation2));
		complaint1.setComplaintType("Complaints is regarding Park");
		complaint1.setDetails("Complaint is Registered");

		Complaint complaint2 = new Complaint();
		complaint2.setId("00002");
		complaint2.setCrn("00020-2016-EW");
		complaint2.setCreatedDate(new DateTime(2016,10,15,0,0).toDate());
		complaint2.setCurrentDate(new DateTime().toDate());
		complaint2.setComplaintDuration(5);
		complaint2.setIsClosed(false);
		complaint2.setLat(20.7980339000101);
		complaint2.setLon(45.05202245);
		complaint2.setComplaintLocation(new GeoPoint(20.7980339000101, 45.05202245));
		complaint2.setComplaintStatus("PROCESSING");
		complaint2.setDepartment(new Department(2, "Roads", "R"));
		complaint2.setAssignee(employee2);
		complaint2.setEscalations(Arrays.asList(escalation1,escalation3));
		complaint2.setComplaintType("Complaints regarding Play grounds");
		complaint2.setDetails("Complaint is in Process");

		Complaint complaint3 = new Complaint();
		complaint3.setId("00003");
		complaint3.setCrn("00030-2016-WV");
		complaint3.setCreatedDate(new DateTime(2016,8,20,0,0).toDate());
		complaint3.setCurrentDate(new DateTime().toDate());
		complaint3.setComplaintDuration(2);
		complaint3.setIsClosed(false);
		complaint3.setLat(55.7980339000101);
		complaint3.setLon(70.05202245);
		complaint3.setComplaintLocation(new GeoPoint(55.7980339000101, 70.05202245));
		complaint3.setComplaintStatus("REGISTERED");
		complaint3.setDepartment(new Department(3, "Buildings", "B"));
		complaint3.setAssignee(employee3);
		complaint3.setEscalations(Arrays.asList(escalation2,escalation3));
		complaint3.setComplaintType("Complaints regarding community hall");
		complaint3.setDetails("Complaint is Registered");

		Complaint complaint4 = new Complaint();
		complaint4.setId("00004");
		complaint4.setCrn("00040-2016-TY");
		complaint4.setCreatedDate(new DateTime(2016,12,5,0,0).toDate());
		complaint4.setCurrentDate(new DateTime().toDate());
		complaint4.setComplaintDuration(10);
		complaint4.setIsClosed(false);
		complaint4.setLat(78.7980339000101);
		complaint4.setLon(96.05202245);
		complaint4.setComplaintLocation(new GeoPoint(78.7980339000101, 96.05202245));
		complaint4.setComplaintStatus("WITHDRAWN");
		complaint4.setDepartment(new Department(4, "Health", "H"));
		complaint4.setAssignee(employee1);
		complaint4.setEscalations(Arrays.asList(escalation1,escalation3));
		complaint4.setComplaintType("Building plan sanction");
		complaint4.setDetails("Complaint is Withdrawn");

		List<Complaint> complaints = Arrays.asList(complaint1,complaint2,complaint3,complaint4);
		complaintService.save(complaints);

	}

	@Test
	public void returnRecordsFromMultipltIndex(){
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
		.withQuery(QueryBuilders.matchQuery("assignee.employeeName", "Ravi"))
		.withIndices("complaint","employee")
		.build();
		
		final List<Complaint> complaints = elasticsearchTemplate.queryForList(searchQuery, Complaint.class);
		System.out.println(" ");
		System.out.println("MULTIPLE INDEXES BASED ON NAME");
		complaints.forEach(complaint -> System.out.println(complaint.getId() + "-" + complaint.getComplaintType()));
	}
	
	@Test
	public void returnRecordsFromMultipltIndexAndMultipleConditions(){
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
		.withQuery(QueryBuilders.matchQuery("assignee.employeeName", "Amar"))
		.withQuery(QueryBuilders.termQuery("assignee.employeeNumber", 100))
		.withIndices("complaint","employee")
		.build();
		
		final List<Complaint> complaints = elasticsearchTemplate.queryForList(searchQuery, Complaint.class);
		System.out.println(" ");
		System.out.println("MULTIPLE INDEXES BASED ON NAME AND EMPLOYEE NUMBER");
		complaints.forEach(complaint -> System.out.println(complaint.getId() + "-" + complaint.getComplaintType()));
	}
	
//	@Test
//	public void updatePartialDocument(){
//		elasticsearchTemplate.refresh(Complaint.class);
////		UpdateRequest updateRequest = new UpdateRequest("complaint", "complaint", "00002");
//		
//		
//		UpdateRequest updateRequest = new UpdateRequest("complaint", "complaint", "00002");
//		updateRequest.source(jsonBuilder()
//            .startObject()
//                .field("gender", "male")
//            .endObject());
//        client.update(updateRequest).get();
//		
//		IndexRequest indexRequest = new IndexRequest();
//		indexRequest.source("details", "Complaint is under Process");
//		UpdateQuery updateQuery = new UpdateQueryBuilder().withId("00002").withIndexName("complaint")
//				.withClass(Complaint.class).withIndexRequest(indexRequest).build();
//		elasticsearchTemplate.update(updateQuery);
//		System.out.println();
//		System.out.println("Updated");
//	}

}

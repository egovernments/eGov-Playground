//package com.elasticsearch.es;
//
//import static java.util.Arrays.asList;
//import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
//
//import java.util.List;
//
//import org.elasticsearch.client.Client;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.joda.time.DateTime;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.geo.GeoPoint;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//
//import com.elasticsearch.config.Config;
//import com.elasticsearch.model.Citizen;
//import com.elasticsearch.model.Complaint1;
//import com.elasticsearch.model.ComplaintStatus;
//import com.elasticsearch.model.Employee;
//import com.elasticsearch.service.ComplaintService;
//import com.elasticsearch.service.EmployeeService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { Config.class }, loader = AnnotationConfigContextLoader.class)
//public class ElasticsearchComplaintTest {
//
//	@Autowired
//	private ElasticsearchTemplate elasticsearchTemplate;
//
//	@Autowired
//	private ComplaintService complaintService;
//
//	@Autowired
//	private EmployeeService employeeService;
//
//	@Autowired
//	private Client client;
//
//	private final Citizen citizen1 = new Citizen("Ronaldo", 8565478565l); 
//	private final Citizen citizen2 = new Citizen("Messi", 465656546546l);
//	private final Citizen citizen3 = new Citizen("Rooney", 989987778898l);
//
//	@Before
//	public void before(){
//
//		elasticsearchTemplate.deleteIndex(Employee.class);
//		elasticsearchTemplate.createIndex(Employee.class);
//		elasticsearchTemplate.putMapping(Employee.class);
//		elasticsearchTemplate.refresh(Employee.class);
//
//		Employee employee1 = new Employee();
//		employee1.setId("0010");
//		employee1.setEmployeeName("suresh");
//		employee1.setEmployeeNumber(500);
//		employeeService.save(employee1);
//
//		Employee employee2 = new Employee();
//		employee2.setId("0020");
//		employee2.setEmployeeName("ramesh");
//		employee2.setEmployeeNumber(800);
//		employeeService.save(employee2);
//
//		Employee employee3 = new Employee();
//		employee3.setId("0030");
//		employee3.setEmployeeName("suresh prabhu");
//		employee3.setEmployeeNumber(700);
//		employeeService.save(employee3);
//
//		elasticsearchTemplate.deleteIndex(Complaint1.class);
//		elasticsearchTemplate.createIndex(Complaint1.class);
//		elasticsearchTemplate.putMapping(Complaint1.class);
//		elasticsearchTemplate.refresh(Complaint1.class);
//
//		Complaint1 complaint1 = new Complaint1();
//		complaint1.setId("0001");
//		complaint1.setCitizens(asList(citizen1,citizen2));
//		complaint1.setCrn("00050-2016-HG");
//		complaint1.setDetails("Construction Of New Road Near Bus stand");
//		complaint1.setCreatedDate(new DateTime(2015,05,27,0,0).toDate());
//		complaint1.setCurrentDate(new DateTime().toDate());
//		complaint1.setComplaintDuration(5);
//		complaint1.setComplaintStatus(new ComplaintStatus("REGISTERED"));
//		complaint1.setIsClosed(false);
//		complaint1.setLat(15.7980339000101);
//		complaint1.setLon(78.05202245);
//		complaint1.setComplaintLocation(new GeoPoint(15.7980339000101, 78.05202245));
//		complaint1.setTitle("Complaint is Registered");
//		complaint1.setEmployeeName("suresh");
//		complaint1.setEmployee(employee1);
//		complaintService.save(complaint1);
//
//		Complaint1 complaint2 = new Complaint1();
//		complaint2.setId("0002");
//		complaint2.setCitizens(asList(citizen3));
//		complaint2.setCrn("00040-2016-EW");
//		complaint2.setDetails("Complaint is Dog Menace in Area");
//		complaint2.setCreatedDate(new DateTime(2015,05,30,12,0).toDate());
//		complaint2.setCurrentDate(new DateTime().toDate());
//		complaint2.setComplaintDuration(10);
//		complaint2.setComplaintStatus(new ComplaintStatus("PROCESSING"));
//		complaint2.setIsClosed(false);
//		complaint2.setLat(45.7980339000101);
//		complaint2.setLon(25.05202245);
//		complaint2.setComplaintLocation(new GeoPoint(45.7980339000101, 25.05202245));
//		complaint2.setTitle("in process");
//		complaint2.setEmployeeName("ramesh");
//		complaint2.setEmployee(employee2);
//		complaintService.save(complaint2);
//
//		Complaint1 complaint3 = new Complaint1();
//		complaint3.setId("0003");
//		complaint3.setCitizens(asList(citizen1,citizen2,citizen3));
//		complaint3.setCrn("00030-2016-WV");
//		complaint3.setDetails("Change of name in voter list");
//		complaint3.setCreatedDate(new DateTime(2016,10,05,20,0).toDate());
//		complaint3.setCurrentDate(new DateTime().toDate());
//		complaint3.setComplaintDuration(10);
//		complaint3.setComplaintStatus(new ComplaintStatus("REGISTERED"));
//		complaint3.setIsClosed(false);
//		complaint3.setLat(55.7980339000101);
//		complaint3.setLon(88.05202245);
//		complaint3.setComplaintLocation(new GeoPoint(55.7980339000101, 88.05202245));
//		complaint3.setTitle("Complaint is Registered");
//		complaint3.setEmployeeName("dinesh");
//		complaint3.setEmployee(employee3);
//		complaintService.save(complaint3);
//
//		//		Complaint complaint4 = new Complaint();
//		//		complaint4.setId("0001");
//		//		complaint4.setCitizens(asList(citizen1,citizen2,citizen3));
//		//		complaint4.setCrn("00070-2016-LK");
//		//		complaint4.setDetails("Building Plan Sanction");
//		//		complaint4.setCreatedDate(new DateTime(2016,10,15,20,0).toDate());
//		//		complaint4.setCurrentDate(new DateTime().toDate());
//		//		complaint4.setComplaintDuration(10);
//		//		complaint4.setComplaintStatus(new ComplaintStatus("COMPLETED"));
//		//		complaint4.setIsClosed(false);
//		//		complaint4.setLat(55.7980339000101);
//		//		complaint4.setLon(88.05202245);
//		//		complaint4.setComplaintLocation(new GeoPoint(55.7980339000101, 88.05202245));
//		//		complaint4.setTitle("Complaint is Registered");
//		//		complaintService.save(complaint4);
//	}
////
//	@Test
//	public void listComplaintBasedOnCrn(){
//		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("crn", "00050-2016-HG")).build();
//		System.out.println();
//		System.out.println("-----------------------------------------------");
//		System.out.println("SEARCH BASED ON CRN");
//		final List<Complaint1> complaints = elasticsearchTemplate.queryForList(searchQuery, Complaint1.class);
//		complaints.forEach(complaint -> System.out.println(complaint.getId() + " " + complaint.getCrn()));
//	}
//
////
////	@Test
////	public void findAll(){
////		Iterable<Complaint1> complaints = complaintService.findAll();
////		System.out.println();
////		System.out.println("-----------------------------------------------");
////		System.out.println("LIST ALL THE COMPLAINTS");
////		complaints.forEach(complaint -> System.out.println(complaint.getCrn() + " " + complaint.getDetails()));
////	}
////
////	@Test
////	public void searchByCrn(){
////		System.out.println();
////		System.out.println("-----------------------------------------------");
////		System.out.println("SEARCH BASED ON CRN WITH PAGING");
////		System.out.println(complaintService.findByCrn("00050-2016-HG", new PageRequest(0, 10)));
////	}
////
////	@Test
////	public void findOne(){
////		System.out.println();
////		System.out.println("-----------------------------------------------");
////		System.out.println("REPOSITORY FIND ONE");
////		System.out.println(complaintService.findOne("0002"));
////	}
////
////	//Test case to match full text
////	@Test
////	public void fullTitleMatch() {
////		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", "Complaint is Registered").operator(AND)).build();
////		final List<Complaint1> complaints = elasticsearchTemplate.queryForList(searchQuery, Complaint1.class);
////		System.out.println();
////		System.out.println("---------------------------------------------");
////		System.out.println("FULL TITLE TEXT SEARCH");
////		complaints.forEach(complaint -> System.out.println(complaint.getTitle()));
////		assertEquals(2, complaints.size());
////	}
////}
////
////	@Test
////	public void searchPartOfTitle() {
////		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", "process")).build();
////		final List<Complaint1> complaints = elasticsearchTemplate.queryForList(searchQuery, Complaint1.class);
////		System.out.println();
////		System.out.println("---------------------------------------------");
////		System.out.println("PART OF THE TITLE SEARCH");
////		complaints.forEach(complaint -> System.out.println(complaint.getTitle()));
////	}
////
//	@Test
//	public void nestedObjectQuery() {
//		final QueryBuilder builder = nestedQuery("citizens", boolQuery().must(termQuery("citizens.name", "Rooney")));
//
//		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
//		final List<Complaint1> complaints = elasticsearchTemplate.queryForList(searchQuery, Complaint1.class);
//		System.out.println();
//		System.out.println("---------------------------------------------");
//		System.out.println("Nested Query");
//		complaints.forEach(complaint -> System.out.println(complaint.getId() +" "+complaint.getDetails()));
//
//	}
//}
////
////
////	@Test
////	public void multimatchQuery() {
////		final SearchQuery searchQuery = new NativeSearchQueryBuilder()
////		.withQuery(multiMatchQuery("in").field("details").field("title")).build();
////
////		final List<Complaint1> complaints = elasticsearchTemplate.queryForList(searchQuery, Complaint1.class);
////		System.out.println();
////		System.out.println("-----------------------------------------------");
////		System.out.println("QUERY ON MULTIPLE FIELDS");
////		complaints.forEach(complaint -> System.out.println(complaint.getDetails()+" - "+ complaint.getTitle()));
////	}
////
////	@Test
////	public void fuzzinessquery() {
////		final SearchQuery searchQuery = new NativeSearchQueryBuilder().
////				withQuery(matchQuery("title", "Complaint in Rigistered").operator(AND).fuzziness(Fuzziness.ONE)).build();
////
////		final List<Complaint1> complaints = elasticsearchTemplate.queryForList(searchQuery, Complaint1.class);
////		System.out.println();
////		System.out.println("-----------------------------------------------");
////		System.out.println("FUZZINESS QUERY");
////		complaints.forEach(complaint -> System.out.println(complaint.getId() +" "+complaint.getTitle()));
////	}
////
////	@Test
////	public void phraseSearch() {
////		final SearchQuery searchQuery = new NativeSearchQueryBuilder().
////				withQuery(matchPhraseQuery("title", "Complaint Registered").slop(1)).build();
////		final List<Complaint1> complaints = elasticsearchTemplate.queryForList(searchQuery, Complaint1.class);
////		System.out.println();
////		System.out.println("-----------------------------------------------");
////		System.out.println("PHRASE SEARCH");
////		complaints.forEach(complaint -> System.out.println(complaint.getId() +" "+complaint.getTitle()));
////	}
////
////	@Test
////	public void aggregationQuery(){
////		TermsBuilder aggregation = AggregationBuilders.terms("complaint_details")
////				.field("details");
////
////		SumBuilder sumAggregation = AggregationBuilders.sum("sum_aggregation").field("complaintDuration");
////		SearchResponse response = client.prepareSearch("pgr_demo")
////				.setTypes("complaint")
////				.addAggregation(sumAggregation)
////				.execute().actionGet();
////
////		System.out.println(response.getAggregations().getAsMap().get("sum_aggregation"));
////		List<Complaint1> complaints = new DefaultResultMapper().mapResults(response, Complaint1.class, new PageRequest(0,10)).getContent();
////		System.out.println();
////		System.out.println("-----------------------------------------------");
////		System.out.println("AGGREGATION RESULTS");
////		complaints.forEach(complaint -> System.out.println(complaint.getId() +" "+complaint.getDetails()));
////	}
////	
////	@Test
////	public void returnSumAggregatedResultForSpecifiedField(){
////		
//////		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//////							.withQuery(QueryBuilders.matchAllQuery())
//////							.withIndices("pgr_demo")
//////							.addAggregation(AggregationBuilders.sum("duration_sum").field("complaintDuration"))
//////							.build();
//////		
//////		
//////		
//////		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
//////			@Override
//////			public Aggregations extract(SearchResponse response) {
//////				return response.getAggregations();
//////			}
//////		});
//////		aggregations.
//////		System.out.println(aggregations.asMap().get("duration_sum"));
//////		
//////		StringTerms count = (StringTerms)aggregations.asMap().get("duration_sum");
//////		
//////		List<String> keys = count.getBuckets()
//////				.stream()
//////				  .map(b -> b.getKeyAsNumber())
//////				  .collect(toList());
////		
//////		MetricsAggregationBuilder aggregation =
//////		        AggregationBuilders
//////		                .sum("agg")
//////		                .field("complaintDuration");
//////		
////		SearchResponse response = client.prepareSearch("pgr_demo")
////				.setTypes("complaint")
//////				.addAggregation(aggregation)
////				.execute().actionGet();
////		
////		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//////		.withQuery(QueryBuilders.matchAllQuery())
////		.withQuery(QueryBuilders.matchQuery("employee.employeeName", "suresh"))
////		.withIndices("pgr_demo")
////		.addAggregation(AggregationBuilders.sum("duration_sum").field("complaintDuration"))
////		.build();
////		
////		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
////		@Override
////		public Aggregations extract(SearchResponse response) {
////			return response.getAggregations();
////		}
////	});
////	
////	System.out.println("-------------------------------------------------------");
////	System.out.println("SUM AGGREGATIONS");
////	Sum agg = aggregations.get("duration_sum");
////    System.out.println(agg.getValue());
////
////		
//////		Sum agg = response.getAggregations().get("agg");
//////		System.out.println(agg.getValue());
////		
////		
////	}
////
////	@Test
////	public void multipleIndicesQuery(){
////		//    	final SearchQuery searchQuery = new NativeSearchQueryBuilder().
////		//    			withQuery(QueryBuilders.matchAllQuery()).withIndices("pgr_demo","employee").
////		//    			build();
////		//    	System.out.println("--------------------------------------------------------");
////		//    	System.out.println("Multiple Indexes");
////		//    	System.out.println(elasticsearchTemplate.count(searchQuery));
////		//    	List<CustomEntity> records = elasticsearchTemplate.queryForList(searchQuery, CustomEntity.class);
////		//    	System.out.println(records);
////		//    	records.forEach(record -> System.out.println(record.getId()));
////		//    	List<Employee> employees = elasticsearchTemplate.queryForList(searchQuery, Employee.class);
////		//    	System.out.println(employees);
////		//    	employees.forEach(employee -> System.out.println(employee.getName()));
////		//    	DefaultResultMapper resultMapper = new DefaultResultMapper(new SimpleElasticsearchMappingContext());
////		//    	resultMapper.
////
////		//    	final QueryBuilder builder = nestedQuery("citizens", boolQuery().must(termQuery("citizens.name", "Rooney")));
////
////	
////		 
////		SearchQuery searchQuery = new NativeSearchQueryBuilder()
////		//        .withQuery(QueryBuilders.termsQuery("employeeName", "suresh","ramesh"))
////		.withQuery(QueryBuilders.matchQuery("employee.employeeName", "suresh prabhu"))
//////		.withQuery(QueryBuilders.termQuery("employee.employeeNumber", 500))
////		.withIndices("pgr_demo","employee")
////		.withTypes("complaint","employee")
////		.build();
////		
////
////		List<Complaint1> sampleEntities = new ArrayList<Complaint1>();
////		String scrollId = elasticsearchTemplate.scan(searchQuery,1000,false);
////
////		Page<Complaint1> page = elasticsearchTemplate.scroll(scrollId, 5000L , Complaint1.class);
////		if(page.hasContent()){
////			sampleEntities.addAll(page.getContent());
////			sampleEntities.forEach(record -> System.out.println(record.getId()+"-"+ 
////					record.getDetails()+"-"+record.getEmployeeName()));
////		}
////
////	}
////}
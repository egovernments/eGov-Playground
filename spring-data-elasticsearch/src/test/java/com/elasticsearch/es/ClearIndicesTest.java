package com.elasticsearch.es;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.elasticsearch.config.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class }, loader = AnnotationConfigContextLoader.class)
public class ClearIndicesTest {

	@Autowired
	 private ElasticsearchTemplate elasticsearchTemplate;
	 
	 @Before
	 public void before(){
		 elasticsearchTemplate.deleteIndex("pgr_demo");
		 elasticsearchTemplate.deleteIndex("blog");
		 elasticsearchTemplate.deleteIndex("test");
	 }
	 
	 @Test
	 public void deleteIndex(){
		 System.out.println("Temproaory Indexes Deleted");
	 }
}

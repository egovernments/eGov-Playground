package org.egov.process.numbergen;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;



public class NumberGenTest {

	
	public void test() {
		
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment()
		  .addClasspathResource("org/egov/process/numbergen/expense_bill.bpmn20.xml")
		  .deploy();

		System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
		repositoryService.createProcessDefinitionQuery().count();

		
	}

}

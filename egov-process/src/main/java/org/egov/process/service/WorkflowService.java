package org.egov.process.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.egov.process.entity.WorkflowTypes;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowService  {
	private static Logger Log = Logger.getLogger(WorkflowService.class);

	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private IdentityService iservice;
	@Autowired
	private WorkflowTypesService workflowTypesService;

	 
	public String start(String message,String bpmnkey) {
		if(bpmnkey.contains("Mail"))
			return email(message,bpmnkey);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("description", message);
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(bpmnkey);
		processInstance.isEnded();
		// Verify that we started a new process instance
		Log.info("Number of process instances: "
				+ runtimeService.createProcessInstanceQuery().count());
		Log.info("Start userId" + processInstance.getStartUserId());
		return processInstance.getProcessInstanceId();
	}

	private String email(String message, String bpmnkey) {
		String from = "manikanta@egovernments.org";
	    boolean male = true;
	    String recipientName = "mani Doe";
	    String recipient = "manikanta@egovernments.org";
	    Date now = new Date();
	    String orderId = "123456";
	    
	    Map<String, Object> vars = new HashMap<String, Object>();
	    vars.put("sender", from);
	    vars.put("recipient", recipient);
	    vars.put("recipientName", recipientName);
	    vars.put("male", male);
	    vars.put("now", now);
	    vars.put("orderId", orderId);
	    
	    runtimeService.startProcessInstanceByKey(bpmnkey, vars);
	  
	    return "check your email";
	}

	@Transactional
	public String process(String userName, String message,
			String processInstanceId) {

		HistoryService historyService = processEngine.getHistoryService();
		HistoricProcessInstance historicProcessInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (historicProcessInstance != null
				&& historicProcessInstance.getEndTime() != null) {
			Log.info("Process instance end time: "
					+ historicProcessInstance.getEndTime());
			return "This Process already completed";
		}

		List<User> list = iservice.createUserQuery().userId(userName).list();
		User user = list.get(0);
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userName)
				.processInstanceId(processInstanceId).list();
		if (tasks.isEmpty()) {
			return "Falure: No tasks for the user-" + userName;
		} else {
			Task s = tasks.get(0);
			taskService.claim(s.getId(), user.getId());
			if (userName.equals("venki")) {
				message = message + "VNo:00001";
			}
			taskService.addComment(s.getId(), processInstanceId, message);
			taskService.complete(s.getId());
			return "Success: User -" + userName + " completed the task";

		}

	}
	
	public void print(DelegateExecution execution)  {
	    String var = (String) execution.getVariable("input");
	    var = var.toUpperCase();
	    execution.setVariable("input", var);
	  }
	
	public void print()  {
	   System.out.println("printing.......................................");
	  }
	
	public boolean initiate(String fullClassname ,Long id,String message)
	{
		WorkflowTypes workflowType = workflowTypesService.findByClassName(fullClassname);
		if(null==workflowType ||null==workflowType.getBusinessKey())
		{
			  System.out.println("BPMN key is empty cant start workflow.");
			  return false;
			  
		} 
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("description", message);
		variables.put("objectId", id);
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(workflowType.getBusinessKey(),variables);
		 processInstance.getProcessVariables();
		
		  System.out.println("Worflow Started .Instance id"+processInstance.getId()+""+processInstance.getProcessVariables());
		
		return true;
	}
	
	public boolean update(String taskId,Long id,String message,String sender)
	{
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//verify
		Map<String, Object> variables = new HashMap<String, Object>();
		taskService.addComment(task.getId(), task.getProcessInstanceId(), message);
		taskService.complete(task.getId(),variables);
		return true;
	}

	
	
}

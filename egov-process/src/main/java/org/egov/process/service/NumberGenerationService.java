package org.egov.process.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.egov.process.config.multitenant.activiti.ProcessEngineThreadLocal;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NumberGenerationService  {
	private static Logger Log = Logger.getLogger(NumberGenerationService.class);

	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private IdentityService iservice;

	 
	public String start(String message,String bpmnkey) {
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

	
	
}

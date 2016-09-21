package org.egov.process.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.egov.process.config.multitenant.activiti.ProcessEngineThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkFlowController {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private HistoryService historyService;

	@RequestMapping("/workflow/start")
	String startWorkflow() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		identityService.setAuthenticatedUserId("frozzie");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(
				ProcessEngineThreadLocal.getTenant() + "_workflow", ProcessEngineThreadLocal.getTenant());
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
				.get(0);
		task.setAssignee("kermit");
		task.setOwner(processInstance.getStartUserId());
		taskService.saveTask(task);
		return "Workflow started and assigned to " + task.getAssignee() + " and the owner is " + task.getOwner()
				+ " Process Id is " + task.getProcessInstanceId();
	}

	@RequestMapping("/workflow/approve")
	String approveWorkflow(@RequestParam("id") String processInstanceId) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		Map<String, Object> variables = new HashMap<>();
		variables.put("action", "Approve");
		taskService.complete(task.getId(), variables);
		HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		return "Workflow has been approved and Process end time is " + instance.getEndTime();
	}

	@RequestMapping("/workflow/reject")
	String rejectWorkflow(@RequestParam("id") String processInstanceId) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		Map<String, Object> variables = new HashMap<>();
		variables.put("action", "");
		taskService.complete(task.getId(), variables);
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		Task newTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		taskService.claim(newTask.getId(), instance.getStartUserId());
		newTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		return "Workflow has been rejected and is assigned to " + newTask.getAssignee();
	}

	@RequestMapping("/workflow/resubmit")
	String reSubmitWorkflow(@RequestParam("id") String processInstanceId) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

		taskService.complete(task.getId());
		Task newTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		taskService.claim(newTask.getId(), "kermit");
		newTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		return "Workflow resubmitted and assigned to " + newTask.getAssignee();
	}
}

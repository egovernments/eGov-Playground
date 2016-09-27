package org.egov.process.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
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

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private FormService formService;

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

	@RequestMapping("/complex/create")
	String startComplexWorkflow(@RequestParam("action") String action,
			@RequestParam(value = "assignee", required = false) String assignee) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		identityService.setAuthenticatedUserId("frozzie");
		Map<String, Object> variables = new HashMap<>();
		variables.put("action", action);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				ProcessEngineThreadLocal.getTenant() + "_complex_workflow", variables);
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
				.get(0);
		if ("Save".equals(action))
			task.setAssignee(processInstance.getStartUserId());
		else
			task.setAssignee(assignee);
		task.setOwner(processInstance.getStartUserId());
		taskService.saveTask(task);
		return "Workflow started and assigned to " + task.getAssignee() + " id is " + processInstance.getId();
	}

	@RequestMapping("/complex/update")
	String UpdateWorkflow(@RequestParam("id") String processInstanceId, @RequestParam("action") String action,
			@RequestParam(value = "assignee", required = false) String assignee) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		Map<String, Object> variables = new HashMap<>();
		variables.put("action", action);
		taskService.complete(task.getId(), variables);
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		Task newTask = null;
		if (!list.isEmpty()) {
			newTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
			if ("Save".equals(action) || "Reject".equals(action))
				assignee = instance.getStartUserId();
			taskService.claim(newTask.getId(), assignee);
		}
		
		return "Workflow has been updated and is assigned to " + assignee;
	}
	
	@RequestMapping("/workflow/getinitdata")
	public String getWorkFlowInitialData() {
		BpmnModel bpmnModel = repositoryService.getBpmnModel(repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(ProcessEngineThreadLocal.getTenant() + "_complex_workflow").list().get(0).getId());
		List<ValuedDataObject> dataObjects = bpmnModel.getProcesses().get(0).getDataObjects();
		String result = "";
		for (ValuedDataObject object : dataObjects)
			result += object.getId() + " Value: " + object.getValue() + "<br />";
		return result;
	}
	
	@RequestMapping("/workflow/gettaskdata")
	public String getWorkFlowTaskData(@RequestParam("id") String processInstanceId) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		Task newTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		String result = "";
		TaskFormData taskFormData = formService.getTaskFormData(newTask.getId());
		for(FormProperty property : taskFormData.getFormProperties()) {
			result += property.getId() + " Value: " + property.getName() + "<br />";
		}
		
		return result;
	}
}

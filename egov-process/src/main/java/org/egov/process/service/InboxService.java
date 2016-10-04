package org.egov.process.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.egov.process.entity.Inbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service 
@Transactional(readOnly = true)
public class InboxService  {
	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private IdentityService iservice;
	
	public List<Inbox> search(String userName){
		List<Inbox> items=new ArrayList<Inbox>();
		SimpleDateFormat ddmmyyy=new SimpleDateFormat("dd/MM/yyyy");
		Inbox item;
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userName).list();
	    tasks.addAll(taskService.createTaskQuery().taskOwner(userName).list());
		for(Task t:tasks)
		{
			ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
			item=new Inbox();
			item.setTaskId(t.getId());
			item.setSender(t.getOwner());
			item.setWfDate(ddmmyyy.format(t.getCreateTime()));
			item.setNatureOfWork(t.getName());
			item.setLink((String)t.getProcessVariables().get("link"));
			item.setDetails((String)process.getProcessVariables().get("description"));
			System.err.println(t.getTaskLocalVariables().get("description"));
			if(process.getProcessVariables().get("objectId")!=null)
			item.setId(Long.valueOf((String)process.getProcessVariables().get("objectId")));
			items.add(item);
		}
		item=new Inbox();
		item.setSender("Sample Sender");
		item.setNatureOfWork("Sample Work");
		item.setLink("Sample Link");
		item.setDetails("Sample details");
		//System.err.println(t.getTaskLocalVariables().get("description"));
		items.add(item);
		
		
		return items;
		
	
	}
}
package org.egov.process.web.controller;

import org.activiti.engine.RuntimeService;
import org.egov.process.config.multitenant.activiti.ProcessEngineThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @RequestMapping("/start")
    String showPublic() {
        return ProcessEngineThreadLocal.getTenant()+ "<br/>" +runtimeService.startProcessInstanceByKey("helloWorld").getProcessDefinitionKey();
    }

    @RequestMapping("/start/specific")
    String showTenantSpecific() {
        return ProcessEngineThreadLocal.getTenant()+ "<br/>" +runtimeService.startProcessInstanceByKey(ProcessEngineThreadLocal.getTenant()+ "_helloWorld").getProcessDefinitionKey();
    }
}

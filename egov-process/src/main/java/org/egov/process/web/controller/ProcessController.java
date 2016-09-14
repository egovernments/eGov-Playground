package org.egov.process.web.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.egov.process.config.multitenant.activiti.ProcessEngineThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @RequestMapping("/public")
    String showPublic() {
        ProcessEngineThreadLocal.setTenant("public");
        runtimeService.startProcessInstanceByKey("helloWorld").getProcessDefinitionKey();
        ProcessEngineThreadLocal.clearTenant();

        return "OK";
    }

    @RequestMapping("/process")
    String showProcess() {
        ProcessEngineThreadLocal.setTenant("tenanta");
        runtimeService.startProcessInstanceByKey("helloWorld").getProcessDefinitionKey();
        ProcessEngineThreadLocal.clearTenant();

        return "OK";
    }
}

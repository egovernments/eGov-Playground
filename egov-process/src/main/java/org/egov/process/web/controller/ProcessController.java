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
    String startProcess() {
        return ProcessEngineThreadLocal.getTenant()+ "<br/>" +
                runtimeService.startProcessInstanceByKeyAndTenantId("helloWorld", ProcessEngineThreadLocal.getTenant()).getProcessDefinitionKey();
    }
}

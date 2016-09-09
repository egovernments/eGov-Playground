package org.egov.process.web.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @RequestMapping("/")
    String show() {
        runtimeService.startProcessInstanceByKey("sample").getProcessDefinitionKey();

        return String.valueOf(historyService.createHistoricProcessInstanceQuery().finished().count());
    }
}

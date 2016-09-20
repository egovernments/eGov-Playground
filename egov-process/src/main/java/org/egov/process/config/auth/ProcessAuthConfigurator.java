package org.egov.process.config.auth;

import org.activiti.engine.cfg.AbstractProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.egov.process.service.UserGroupService;
import org.egov.process.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessAuthConfigurator extends AbstractProcessEngineConfigurator{

    @Autowired
    private UserService userService;

    @Autowired
    private UserGroupService userGroupService;

    public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setUserEntityManager(new ProcessUserManager(processEngineConfiguration, userService));
        processEngineConfiguration.setGroupEntityManager(new ProcessUserGroupManager(processEngineConfiguration, userGroupService));
    }
}

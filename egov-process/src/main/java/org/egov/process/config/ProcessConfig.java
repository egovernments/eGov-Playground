package org.egov.process.config;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.spring.SpringExpressionManager;
import org.egov.process.ResourceFinderUtil;
import org.egov.process.config.auth.ProcessAuthConfigurator;
import org.egov.process.config.multitenant.activiti.AsyncExecuterPerTenant;
import org.egov.process.config.multitenant.activiti.DBSqlSessionFactory;
import org.egov.process.config.multitenant.activiti.TenantIdentityHolder;
import org.egov.process.config.multitenant.activiti.TenantawareDatasource;
import org.egov.process.web.filter.TenantAwareProcessFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.activiti.engine.ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;
import static org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl.DATABASE_TYPE_POSTGRES;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

@EnableJpaRepositories(basePackages = "org.egov.process.repository")
@Configuration
@Order(LOWEST_PRECEDENCE)
public class ProcessConfig {

    @Autowired
    ProcessAuthConfigurator processAuthConfigurator;

    @Autowired
    private ApplicationContext appContext;

    @Bean
    @DependsOn("tenants")
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration(EntityManagerFactory entityManagerFactory,
                                                                                TenantIdentityHolder tenantIdentityHolder,
                                                                                TenantawareDatasource tenantawareDatasource) {
        MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfig = new MultiSchemaMultiTenantProcessEngineConfiguration(tenantIdentityHolder);
        processEngineConfig.setDataSource(tenantawareDatasource);
        processEngineConfig.setTransactionsExternallyManaged(false);
        processEngineConfig.setAsyncExecutorActivate(true);
        //processEngineConfig.setAsyncExecutorEnabled(true);
        processEngineConfig.setAsyncExecutor(new AsyncExecuterPerTenant(tenantIdentityHolder));
        processEngineConfig.setJpaCloseEntityManager(false);
        processEngineConfig.setJpaHandleTransaction(false);
        processEngineConfig.setJpaEntityManagerFactory(entityManagerFactory);
        processEngineConfig.setDatabaseSchemaUpdate(DB_SCHEMA_UPDATE_TRUE);
        processEngineConfig.setDatabaseType(DATABASE_TYPE_POSTGRES);
        processEngineConfig.setHistory(HistoryLevel.FULL.getKey());
        processEngineConfig.setDbSqlSessionFactory(new DBSqlSessionFactory());
        processEngineConfig.setTablePrefixIsSchema(true);
        processEngineConfig.setExpressionManager(new SpringExpressionManager(appContext, null));
        // processEngineConfig.setDeploymentMode("resource-parent-folder");
        processEngineConfig.setConfigurators(Arrays.asList(processAuthConfigurator));
        tenantIdentityHolder.getAllTenants().stream().filter(Objects::nonNull).forEach(tenant ->
                processEngineConfig.registerTenant(tenant, tenantawareDatasource)
        );

        return processEngineConfig;
    }


    @Bean
    ProcessEngine processEngine(MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration,
                                TenantIdentityHolder tenantIdentityHolder) throws Exception {
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        ResourceFinderUtil resourceResolver = new ResourceFinderUtil();


        tenantIdentityHolder.getAllTenants().forEach(tenant -> {
            tenantIdentityHolder.setCurrentTenantId(tenant);
            List<Resource> resources  =
                    resourceResolver.getResources("classpath:processes/main/*.bpmn",
                            "classpath:processes/"+tenant+"/*.bpmn",
                            "classpath:processes/main/*.bpmn20.xml",
                            "classpath:processes/"+tenant+"/*.bpmn20.xml"
                    );
            resources.forEach( resource -> {
                try {
                    processEngine.getRepositoryService().createDeployment().
                            addInputStream(resource.getFilename(), resource.getInputStream())
                            .deploy();
                } catch (Exception e) {
                    throw new RuntimeException("Error while deploying BPMN file", e);
                }
            });

            tenantIdentityHolder.clearCurrentTenantId();
        });
        return processEngine;
    }

    @Bean
    ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }

    @Bean
    IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

    @Bean
    TenantAwareProcessFilter tenantAwareProcessFilter() {
        return new TenantAwareProcessFilter();
    }
}

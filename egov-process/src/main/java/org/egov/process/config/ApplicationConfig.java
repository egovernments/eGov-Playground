package org.egov.process.config;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.asyncexecutor.multitenant.ExecutorPerTenantAsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.multitenant.SharedExecutorServiceAsyncExecutor;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.egov.process.config.multitenant.DBSqlSessionFactory;
import org.egov.process.config.multitenant.ProcessEngineThreadLocal;
import org.egov.process.config.multitenant.TenantIdentityHolder;
import org.egov.process.config.multitenant.TenantawareDatasource;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.activiti.engine.ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP;
import static org.activiti.engine.ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;
import static org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl.DATABASE_TYPE_POSTGRES;
import static org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_CREATE;
import static org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_DROP_CREATE;
import static org.hibernate.cfg.AvailableSettings.AUTO_CLOSE_SESSION;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.JTA_PLATFORM;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER;
import static org.hibernate.cfg.AvailableSettings.USE_STREAMS_FOR_BINARY;

@EnableJpaRepositories(basePackages = "org.egov.process.repository")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Configuration
public class ApplicationConfig {

    @Autowired
    Environment env;

    @Autowired
    ConfigurableEnvironment environment;

    @Bean(name = "tenants", autowire = Autowire.BY_NAME)
    public List<String> tenants() {
        final List<String> tenants = new ArrayList<>();
        environment.getPropertySources().iterator().forEachRemaining(propertySource -> {
            if (propertySource instanceof MapPropertySource)
                ((MapPropertySource) propertySource).getSource().forEach((key, value) -> {
                    if (key.startsWith("tenant."))
                        tenants.add(value.toString());
                });
        });
        return tenants;
    }

    @Bean
    JndiObjectFactoryBean dataSource() {
        final JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
        dataSource.setExpectedType(DataSource.class);
        dataSource.setJndiName(env.getProperty("default.jdbc.jndi.dataSource"));
        return dataSource;
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new JtaTransactionManager();
    }

    @Bean
    EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJtaDataSource(dataSource);
        entityManagerFactory.setPersistenceUnitName("process-pu");
        entityManagerFactory.setPackagesToScan("org.egov.process.entity");
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdaper());
        entityManagerFactory.setJpaPropertyMap(additionalProperties());
        entityManagerFactory.setValidationMode(ValidationMode.NONE);
        entityManagerFactory.setSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean
    JpaVendorAdapter jpaVendorAdaper() {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(env.getProperty("jpa.database", Database.class));
        vendorAdapter.setGenerateDdl(env.getProperty("jpa.generateDdl", Boolean.class));
        return vendorAdapter;
    }

    Map<String, Object> additionalProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.validator.apply_to_ddl", false);
        properties.put("hibernate.validator.autoregister_listeners", false);
        properties.put(DIALECT, env.getProperty(DIALECT));
        properties.put(JTA_PLATFORM, env.getProperty(JTA_PLATFORM));
        properties.put(AUTO_CLOSE_SESSION, env.getProperty(AUTO_CLOSE_SESSION));
        properties.put(USE_STREAMS_FOR_BINARY, env.getProperty(USE_STREAMS_FOR_BINARY));
        properties.put(MULTI_TENANT, env.getProperty(MULTI_TENANT));
        properties.put("hibernate.database.type", env.getProperty("jpa.database"));
        properties.put(MULTI_TENANT_CONNECTION_PROVIDER, "org.egov.process.config.multitenant.MultiTenantSchemaConnectionProvider");
        properties.put(MULTI_TENANT_IDENTIFIER_RESOLVER, "org.egov.process.config.multitenant.DomainBasedSchemaTenantIdentifierResolver");

        return properties;
    }


    @Bean
    @DependsOn("tenants")
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration(EntityManagerFactory entityManagerFactory,
                                                                TenantIdentityHolder tenantIdentityHolder,
                                                                TenantawareDatasource tenantawareDatasource) {
        MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfig = new MultiSchemaMultiTenantProcessEngineConfiguration(tenantIdentityHolder);
        processEngineConfig.setDataSource(tenantawareDatasource);
        processEngineConfig.setTransactionsExternallyManaged(true);
        processEngineConfig.setAsyncExecutorActivate(true);
        //processEngineConfig.setAsyncExecutorEnabled(true);
        processEngineConfig.setAsyncExecutor(new SharedExecutorServiceAsyncExecutor(tenantIdentityHolder));
        processEngineConfig.setJpaCloseEntityManager(true);
        processEngineConfig.setJpaHandleTransaction(true);
        processEngineConfig.setJpaEntityManagerFactory(entityManagerFactory);
        processEngineConfig.setDatabaseSchemaUpdate(DB_SCHEMA_UPDATE_TRUE);
        processEngineConfig.setDatabaseType(DATABASE_TYPE_POSTGRES);
        processEngineConfig.setHistory(HistoryLevel.FULL.getKey());
        processEngineConfig.setDbSqlSessionFactory(new DBSqlSessionFactory());
        processEngineConfig.setTablePrefixIsSchema(true);
       // processEngineConfig.setDeploymentMode("resource-parent-folder");
       tenantIdentityHolder.getAllTenants().parallelStream().filter(Objects::nonNull).forEach(tenant ->
            processEngineConfig.registerTenant(tenant, tenantawareDatasource)
        );

        return processEngineConfig;
    }


    @Bean
    ProcessEngine processEngine(MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration) throws Exception {
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource [] resources =  resourceResolver.getResources("classpath:process/**/*.bpmn");

        tenants().parallelStream().forEach(tenant -> {
            ProcessEngineThreadLocal.setTenant(tenant);
                    for (Resource resource : resources) {
                        try {
                            processEngine.getRepositoryService().createDeployment().
                                    addInputStream(resource.getFilename(), resource.getInputStream())
                                    .deploy();

                        } catch (Exception e) {
                          //Ignore
                        }
                    }
            ProcessEngineThreadLocal.clearTenant();
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

}

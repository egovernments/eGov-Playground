package org.egov.process.config;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.cfg.AvailableSettings.AUTO_CLOSE_SESSION;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.JTA_PLATFORM;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER;
import static org.hibernate.cfg.AvailableSettings.USE_STREAMS_FOR_BINARY;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Configuration
@Order(HIGHEST_PRECEDENCE)
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
        properties.put(MULTI_TENANT_CONNECTION_PROVIDER, "org.egov.process.config.multitenant.hibernate.MultiTenantSchemaConnectionProvider");
        properties.put(MULTI_TENANT_IDENTIFIER_RESOLVER, "org.egov.process.config.multitenant.hibernate.DomainBasedSchemaTenantIdentifierResolver");

        return properties;
    }

}

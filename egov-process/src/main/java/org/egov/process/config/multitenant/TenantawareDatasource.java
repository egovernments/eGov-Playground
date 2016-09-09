package org.egov.process.config.multitenant;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cfg.multitenant.TenantAwareDataSource;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@DependsOn("dataSource")
public class TenantawareDatasource extends TenantAwareDataSource {

    DataSource dataSource;

    @Autowired
    Environment env;

    String databaseType;

    @Autowired
    public TenantawareDatasource(@Qualifier("tenantIdentityHolder") TenantInfoHolder tenantInfoHolder,
                                 DataSource dataSource) {
        super(tenantInfoHolder);
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        final Connection connection = dataSource.getConnection();
        String tenantId = ProcessEngineThreadLocal.getTenant();
            if ("POSTGRESQL".equals(databaseType))
                connection.createStatement().execute("SET SCHEMA '" + tenantId + "'");
            else
                connection.createStatement().execute("USE " + tenantId);
        return connection;
    }

    protected DataSource getCurrentDataSource() {
        if(dataSource == null) {
            throw new ActivitiException("Could not find a dataSource");
        } else {
            return dataSource;
        }
    }

    @PostConstruct
    public void setDatabaseType() {
        databaseType = env.getProperty("jpa.database");
    }

}

package org.egov.process.config.multitenant;

import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;

public class DBSqlSessionFactory extends DbSqlSessionFactory {

    public Session openSession(CommandContext commandContext) {
        setDatabaseSchema(ProcessEngineThreadLocal.getTenant());
        return super.openSession(commandContext);
    }
}

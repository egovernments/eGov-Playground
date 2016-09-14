package org.egov.process.config.multitenant.activiti;

import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Component("tenantIdentityHolder")
@DependsOn({"dataSource", "tenants"})
public class TenantIdentityHolder implements TenantInfoHolder {

    @Resource
    private List<String> tenants;

    @Override
    public Collection<String> getAllTenants() {
        return tenants;
    }

    @Override
    public void setCurrentTenantId(final String s) {
        ProcessEngineThreadLocal.setTenant(s);
    }

    @Override
    public String getCurrentTenantId() {
        return ProcessEngineThreadLocal.getTenant();
    }

    @Override
    public void clearCurrentTenantId() {
        ProcessEngineThreadLocal.clearTenant();
    }
}

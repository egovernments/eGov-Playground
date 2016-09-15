package org.egov.process.web.filter;

import org.egov.process.config.multitenant.activiti.ProcessEngineThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TenantAwareProcessFilter implements Filter {

    @Autowired
    Environment environment;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        try {
            final String domainURL = extractRequestedDomainName(((HttpServletRequest) request).getRequestURL().toString());
            ProcessEngineThreadLocal.setTenant(environment.getProperty("tenant." + domainURL));
            chain.doFilter(request, response);
        } finally {
            ProcessEngineThreadLocal.clearTenant();
        }
    }

    @Override
    public void destroy() {

    }

    private static String extractRequestedDomainName(final String requestURL) {
        final int domainNameStartIndex = requestURL.indexOf("://") + 3;
        String domainName = requestURL.substring(domainNameStartIndex, requestURL.indexOf('/', domainNameStartIndex));
        if (domainName.contains(":"))
            domainName = domainName.split(":")[0];
        return domainName;
    }
}

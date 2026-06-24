package com.pasindu.saasmultitenentapp.config;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import java.io.IOException;
import jakarta.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter implements Filter{
    private static final String TENANT_ID_HEADER = "X-Tenant-Id";
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String tenantId = resolverHeader(httpRequest);
        if (tenantId == null || tenantId.isBlank()) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Tenant ID is required");
            httpResponse.setContentType("application/json");
            // httpResponse.getWriter().flush();
            return;
        }
        try {
            TenantContext.setCurrentTenant(tenantId);
            filterChain.doFilter(httpRequest, httpResponse);
        } finally {
            TenantContext.clear();
        }
    }

    private String resolverHeader(final HttpServletRequest request) {
        final String tenantId = request.getHeader(TENANT_ID_HEADER);
        if (tenantId != null && !tenantId.isEmpty()) {
            return tenantId.toLowerCase();
        }
        return null;
    }
}

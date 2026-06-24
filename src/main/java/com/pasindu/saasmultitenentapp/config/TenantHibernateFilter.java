package com.pasindu.saasmultitenentapp.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.aspectj.lang.annotation.Before;
@Aspect
@Component
public class TenantHibernateFilter {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Before("execution(* com.pasindu.saasmultitenentapp.services.impl.*ServiceImpl.*(..))")
    public void activateTenantFilter() {
        final String tenantId = TenantContext.getCurrentTenant();
        if (tenantId != null) {
            final Session session = entityManager.unwrap(Session.class);
            session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
        }
    }
}
